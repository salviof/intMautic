package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticTags;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticMetaTags;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMauticTags(tipo = FabMauticMetaTags.VINCULAR_ESTAGIO_CONTATO)
public class IntegracaoRestMauticVincularEstagioContato
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticVincularEstagioContato(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticMetaTags.VINCULAR_ESTAGIO_CONTATO, pTipoAgente,
				pUsuario, pParametro);
	}

	/**
	 * Parâmetros: id do estágio, id do contato (enviados na url, sem corpo)
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do estágio");
		}
		if (parametros.size() <= 1 || parametros.get(1) == null
				|| parametros.get(1).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do contato");
		}
		return "";
	}
}
