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

@InfoIntegracaoRestMauticFields(tipo = FabMauticFields.CAMPO_CTR_SALVAR_EDITAR)
public class IntegracaoRestMauticCampoCtrSalvarEditar
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestMauticCampoCtrSalvarEditar(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabMauticFields.CAMPO_CTR_SALVAR_EDITAR, pTipoAgente, pUsuario,
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
	 * Parâmetros: id, label, group, defaultValue, isRequired, isPublished,
	 * properties (opcional, JsonObject)
	 *
	 * Apenas os campos informados (não nulos) são enviados, alias e type não
	 * são alterados após a criação.
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null || parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do campo");
		}
		String[] campos = {"label", "group", "defaultValue", "isRequired", "isPublished", "properties"};
		JSONObject json = new JSONObject();
		for (int i = 0; i < campos.length; i++) {
			int indice = 1 + i;
			if (parametros.size() > indice && parametros.get(indice) != null) {
				if ("properties".equals(campos[i])) {
					json.put(campos[i], getProperties(parametros.get(indice)));
				} else {
					json.put(campos[i], parametros.get(indice));
				}
			}
		}
		if (json.isEmpty()) {
			throw new UnsupportedOperationException("Informe ao menos um campo para edição");
		}
		return json.toJSONString();
	}
}
