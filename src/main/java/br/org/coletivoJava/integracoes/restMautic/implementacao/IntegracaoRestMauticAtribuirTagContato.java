package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticTags;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticMetaTags;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import jakarta.json.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticTags(tipo = FabMauticMetaTags.ATRIBUIR_TAG_CONTATO)
public class IntegracaoRestMauticAtribuirTagContato
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	private static final long VALIDADE_CACHE_MS = 60 * 60 * 1000L;

	/**
	 * Nome da tag no Mautic por id, com o momento da consulta
	 */
	private static final Map<String, NomeTagConsultado> CACHE_NOMES_TAGS = new ConcurrentHashMap<>();

	private static class NomeTagConsultado {

		private final String nome;
		private final long momentoConsulta;

		private NomeTagConsultado(String pNome) {
			nome = pNome;
			momentoConsulta = System.currentTimeMillis();
		}

		private boolean isExpirado() {
			return System.currentTimeMillis() - momentoConsulta > VALIDADE_CACHE_MS;
		}
	}

	public IntegracaoRestMauticAtribuirTagContato(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticMetaTags.ATRIBUIR_TAG_CONTATO, pTipoAgente, pUsuario,
				pParametro);
	}

	/**
	 * Limpa o cache de nomes de tags, deve ser chamado ao criar ou alterar uma
	 * tag no Mautic
	 */
	public static void limparCache() {
		CACHE_NOMES_TAGS.clear();
	}

	private static boolean isIdValido(String pId) {
		return pId.matches("\\d+") && Long.parseLong(pId) > 0;
	}

	/**
	 * Aceita uma Collection ou array de ids, ou um único id (String ou
	 * Number)
	 */
	private List<String> getIdsTags() {
		if (parametros.size() <= 1 || parametros.get(1) == null) {
			throw new UnsupportedOperationException("Informe ao menos um id de tag");
		}
		Object parametroTags = parametros.get(1);
		Collection<?> valores;
		if (parametroTags instanceof Collection) {
			valores = (Collection<?>) parametroTags;
		} else if (parametroTags instanceof Object[]) {
			valores = Arrays.asList((Object[]) parametroTags);
		} else if (parametroTags instanceof String || parametroTags instanceof Number) {
			valores = Arrays.asList(parametroTags);
		} else {
			throw new UnsupportedOperationException("Parâmetro de tags inválido, informe uma lista de ids ou um id, recebido "
					+ parametroTags.getClass().getSimpleName());
		}
		Set<String> ids = new LinkedHashSet<>();
		for (Object valor : valores) {
			if (valor == null) {
				throw new UnsupportedOperationException("A lista de ids de tags contém um valor nulo");
			}
			String id = valor.toString().trim();
			if (!isIdValido(id)) {
				throw new UnsupportedOperationException("Id de tag inválido: " + valor);
			}
			ids.add(id);
		}
		if (ids.isEmpty()) {
			throw new UnsupportedOperationException("Informe ao menos um id de tag");
		}
		return new ArrayList<>(ids);
	}

	/**
	 * O endpoint de edição do contato identifica as tags pelo nome (um id seria
	 * criado como uma nova tag com o número no nome)
	 */
	private String getNomeTag(String pIdTag) {
		NomeTagConsultado consultado = CACHE_NOMES_TAGS.get(pIdTag);
		if (consultado != null && !consultado.isExpirado()) {
			return consultado.nome;
		}
		String nome = consultarNomeTag(pIdTag);
		CACHE_NOMES_TAGS.put(pIdTag, new NomeTagConsultado(nome));
		return nome;
	}

	private String consultarNomeTag(String pIdTag) {
		ItfRespostaWebServiceSimples resposta = FabMauticMetaTags.TAG_DETALHES.getAcao(pIdTag).getResposta();
		if (resposta == null || !resposta.isSucesso()) {
			throw new UnsupportedOperationException("Tag " + pIdTag + " não encontrada no Mautic - "
					+ (resposta == null ? "sem resposta" : resposta.getRespostaTexto()));
		}
		JsonObject json = resposta.getRespostaComoObjetoJson();
		if (json == null || json.getJsonObject("tag") == null) {
			throw new UnsupportedOperationException("Resposta inesperada obtendo a tag " + pIdTag);
		}
		String nome = json.getJsonObject("tag").getString("tag", "").trim();
		if (nome.isEmpty()) {
			throw new UnsupportedOperationException("A tag " + pIdTag + " não possui nome no Mautic");
		}
		return nome;
	}

	/**
	 * Parâmetros: id do contato, ids das tags (Collection ou array de ids, ou
	 * um único id)
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| !isIdValido(parametros.get(0).toString().trim())) {
			throw new UnsupportedOperationException("Informe um id de contato válido");
		}
		JSONArray jsonTags = new JSONArray();
		for (String idTag : getIdsTags()) {
			jsonTags.add(getNomeTag(idTag));
		}
		JSONObject json = new JSONObject();
		json.put("tags", jsonTags);
		return json.toJSONString();
	}
}
