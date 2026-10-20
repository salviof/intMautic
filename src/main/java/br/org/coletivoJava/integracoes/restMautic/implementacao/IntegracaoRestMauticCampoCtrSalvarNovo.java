package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticFields;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticFields;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import jakarta.json.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@InfoIntegracaoRestMauticFields(tipo = FabMauticFields.CAMPO_CTR_SALVAR_NOVO)
public class IntegracaoRestMauticCampoCtrSalvarNovo
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticCampoCtrSalvarNovo(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticFields.CAMPO_CTR_SALVAR_NOVO, pTipoAgente, pUsuario,
				pParametro);
	}

	/**
	 * O parâmetro properties é opcional (nulo não é enviado) e deve ser um
	 * JsonObject
	 */
	private JSONObject getProperties(Object pProperties) {
		if (!(pProperties instanceof JsonObject)) {
			throw new UnsupportedOperationException("Parâmetro properties deve ser um JsonObject, recebido "
					+ pProperties.getClass().getSimpleName());
		}
		try {
			return (JSONObject) new JSONParser().parse(pProperties.toString());
		} catch (ParseException e) {
			throw new UnsupportedOperationException("Parâmetro properties não é um JSON válido: " + e.getMessage());
		}
	}

	/**
	 * Parâmetros: label, alias (opcional, gerado a partir do label), type,
	 * group, defaultValue, isRequired, isPublished, properties (opcional,
	 * JsonObject)
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null || parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro label");
		}
		if (parametros.size() <= 2 || parametros.get(2) == null || parametros.get(2).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro type");
		}
		String[] campos = {"label", "alias", "type", "group", "defaultValue", "isRequired", "isPublished", "properties"};
		JSONObject json = new JSONObject();
		for (int i = 0; i < campos.length; i++) {
			int indice = i;
			if (parametros.size() > indice && parametros.get(indice) != null) {
				if ("properties".equals(campos[i])) {
					json.put(campos[i], getProperties(parametros.get(indice)));
				} else if (!"alias".equals(campos[i]) || !parametros.get(indice).toString().trim().isEmpty()) {
					json.put(campos[i], parametros.get(indice));
				}
			}
		}
		return json.toJSONString();
	}
}
