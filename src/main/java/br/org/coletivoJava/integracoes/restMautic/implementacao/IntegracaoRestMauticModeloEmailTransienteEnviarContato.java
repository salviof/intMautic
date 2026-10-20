package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.MODELO_EMAIL_TRANSIENTE_ENVIAR_CONTATO)
public class IntegracaoRestMauticModeloEmailTransienteEnviarContato
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticModeloEmailTransienteEnviarContato(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.MODELO_EMAIL_TRANSIENTE_ENVIAR_CONTATO,
				pTipoAgente, pUsuario, pParametro);
	}
}