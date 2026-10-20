package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticEmail;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticModeloEmail;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMauticEmail(tipo = FabMauticModeloEmail.EMAIL_DETALHES)
public class IntegracaoRestMauticEmailDetalhes
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticEmailDetalhes(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticModeloEmail.EMAIL_DETALHES, pTipoAgente, pUsuario,
				pParametro);
	}
}