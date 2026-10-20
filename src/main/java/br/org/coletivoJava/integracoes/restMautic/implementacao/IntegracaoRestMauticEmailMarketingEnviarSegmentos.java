package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.EMAIL_MARKETING_ENVIAR_SEGMENTOS)
public class IntegracaoRestMauticEmailMarketingEnviarSegmentos
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticEmailMarketingEnviarSegmentos(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.EMAIL_MARKETING_ENVIAR_SEGMENTOS,
				pTipoAgente, pUsuario, pParametro);
	}

	/**
	 * Parâmetro: id do e-mail. O envio não possui corpo, o Mautic envia aos
	 * contatos dos segmentos vinculados ao e-mail que ainda não o receberam.
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do e-mail");
		}
		return "";
	}
}
