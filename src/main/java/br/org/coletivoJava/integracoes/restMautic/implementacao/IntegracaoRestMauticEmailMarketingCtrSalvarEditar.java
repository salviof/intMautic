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

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.EMAIL_MARKETING_CTR_SALVAR_EDITAR)
public class IntegracaoRestMauticEmailMarketingCtrSalvarEditar
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticEmailMarketingCtrSalvarEditar(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.EMAIL_MARKETING_CTR_SALVAR_EDITAR,
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
	 * Parâmetros: id, name, subject, customHtml, description, lists (ids dos
	 * segmentos: Collection, array ou um único id), isPublished
	 *
	 * Apenas os campos informados (não nulos) são enviados, o emailType é
	 * mantido em "list".
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do e-mail");
		}
		String[] campos = {"name", "subject", "customHtml", "description", "lists", "isPublished"};
		JSONObject json = new JSONObject();
		for (int i = 0; i < campos.length; i++) {
			int indice = 1 + i;
			if (parametros.size() > indice && parametros.get(indice) != null) {
				if ("lists".equals(campos[i])) {
					JSONArray segmentos = getIdsSegmentos(parametros.get(indice));
					if (segmentos.isEmpty()) {
						throw new UnsupportedOperationException("O e-mail marketing precisa de ao menos um segmento (lists)");
					}
					json.put(campos[i], segmentos);
				} else {
					json.put(campos[i], parametros.get(indice));
				}
			}
		}
		if (json.isEmpty()) {
			throw new UnsupportedOperationException("Informe ao menos um campo para edição");
		}
		json.put("emailType", "list");
		return json.toJSONString();
	}
}
