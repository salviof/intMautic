package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.MODELO_EMAIL_MARKETING_CTR_SALVAR_NOVO)
public class IntegracaoRestMauticModeloEmailMarketingCtrSalvarNovo
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticModeloEmailMarketingCtrSalvarNovo(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.MODELO_EMAIL_MARKETING_CTR_SALVAR_NOVO,
				pTipoAgente, pUsuario, pParametro);
	}

	/**
	 * Parâmetros: name, subject, customHtml, description, isPublished
	 *
	 * O emailType é fixado em "template" (modelo de e-mail marketing
	 * reutilizado por campanhas e automações, sem segmentos).
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
		json.put("emailType", "template");
		//tema "code mode": o Mautic usa o customHtml enviado, sem o construtor visual
		json.put("template", "mautic_code_mode");
		json.put("name", parametros.get(0));
		json.put("subject", parametros.get(1));
		json.put("customHtml", parametros.get(2));
		if (parametros.size() > 3 && parametros.get(3) != null) {
			json.put("description", parametros.get(3));
		}
		if (parametros.size() > 4 && parametros.get(4) != null) {
			json.put("isPublished", parametros.get(4));
		}
		return json.toJSONString();
	}
}
