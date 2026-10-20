package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.MODELO_EMAIL_MARKETING_CTR_SALVAR_EDITAR)
public class IntegracaoRestMauticModeloEmailMarketingCtrSalvarEditar
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticModeloEmailMarketingCtrSalvarEditar(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.MODELO_EMAIL_MARKETING_CTR_SALVAR_EDITAR,
				pTipoAgente, pUsuario, pParametro);
	}

	/**
	 * Parâmetros: id, name, subject, customHtml, description, isPublished
	 *
	 * Apenas os campos informados (não nulos) são enviados, o emailType é
	 * mantido em "template".
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do e-mail");
		}
		String[] campos = {"name", "subject", "customHtml", "description", "isPublished"};
		JSONObject json = new JSONObject();
		for (int i = 0; i < campos.length; i++) {
			int indice = 1 + i;
			if (parametros.size() > indice && parametros.get(indice) != null) {
				json.put(campos[i], parametros.get(indice));
			}
		}
		if (json.isEmpty()) {
			throw new UnsupportedOperationException("Informe ao menos um campo para edição");
		}
		json.put("emailType", "template");
		return json.toJSONString();
	}
}
