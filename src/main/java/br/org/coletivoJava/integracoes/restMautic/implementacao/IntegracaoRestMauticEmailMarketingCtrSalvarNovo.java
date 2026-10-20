package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import java.util.Arrays;
import java.util.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.EMAIL_MARKETING_CTR_SALVAR_NOVO)
public class IntegracaoRestMauticEmailMarketingCtrSalvarNovo
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticEmailMarketingCtrSalvarNovo(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.EMAIL_MARKETING_CTR_SALVAR_NOVO,
				pTipoAgente, pUsuario, pParametro);
	}

	/**
	 * Aceita uma Collection ou array de ids de segmentos, ou um único id
	 * (String ou Number)
	 */
	private JSONArray getIdsSegmentos(Object pSegmentos) {
		Collection<?> valores;
		if (pSegmentos instanceof Collection) {
			valores = (Collection<?>) pSegmentos;
		} else if (pSegmentos instanceof Object[]) {
			valores = Arrays.asList((Object[]) pSegmentos);
		} else if (pSegmentos instanceof String || pSegmentos instanceof Number) {
			valores = Arrays.asList(pSegmentos);
		} else {
			throw new UnsupportedOperationException("Parâmetro lists inválido, informe uma lista de ids ou um id, recebido "
					+ pSegmentos.getClass().getSimpleName());
		}
		JSONArray ids = new JSONArray();
		for (Object valor : valores) {
			String id = valor == null ? "" : valor.toString().trim();
			if (!id.matches("\\d+") || Long.parseLong(id) <= 0) {
				throw new UnsupportedOperationException("Id de segmento inválido: " + valor);
			}
			Long idNumerico = Long.parseLong(id);
			if (!ids.contains(idNumerico)) {
				ids.add(idNumerico);
			}
		}
		return ids;
	}

	/**
	 * Parâmetros: name, subject, customHtml, description, lists (opcional, ids
	 * dos segmentos: Collection, array ou um único id), isPublished
	 *
	 * O emailType é fixado em "list" (e-mail marketing enviado a segmentos).
	 */
	@Override
	public String gerarCorpoRequisicao() {
		String[] obrigatorios = {"name", "subject", "customHtml"};
		for (int i = 0; i < obrigatorios.length; i++) {
			if (parametros.size() <= i || parametros.get(i) == null
					|| parametros.get(i).toString().trim().isEmpty()) {
				throw new UnsupportedOperationException("Informe o parâmetro " + obrigatorios[i]);
			}
		}
		JSONObject json = new JSONObject();
		json.put("emailType", "list");
		json.put("name", parametros.get(0));
		json.put("subject", parametros.get(1));
		json.put("customHtml", parametros.get(2));
		if (parametros.size() > 3 && parametros.get(3) != null) {
			json.put("description", parametros.get(3));
		}
		if (parametros.size() > 4 && parametros.get(4) != null) {
			json.put("lists", getIdsSegmentos(parametros.get(4)));
		}
		if (parametros.size() > 5 && parametros.get(5) != null) {
			json.put("isPublished", parametros.get(5));
		}
		return json.toJSONString();
	}
}
