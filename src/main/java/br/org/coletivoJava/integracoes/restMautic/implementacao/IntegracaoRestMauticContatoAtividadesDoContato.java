package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticRest;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestMauticRest(tipo = FabMauticContatoRest.CONTATO_ATIVIDADES_DO_CONTATO)
public class IntegracaoRestMauticContatoAtividadesDoContato
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticContatoAtividadesDoContato(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticContatoRest.CONTATO_ATIVIDADES_DO_CONTATO, pTipoAgente,
				pUsuario, pParametro);
	}
}