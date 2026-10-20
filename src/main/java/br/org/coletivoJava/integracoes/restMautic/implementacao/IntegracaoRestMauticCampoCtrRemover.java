package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticFields;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticFields;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMauticFields(tipo = FabMauticFields.CAMPO_CTR_REMOVER)
public class IntegracaoRestMauticCampoCtrRemover
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticCampoCtrRemover(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticFields.CAMPO_CTR_REMOVER, pTipoAgente, pUsuario,
				pParametro);
	}

	/**
	 * Parâmetro: id do campo. A remoção não possui corpo.
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null || parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do campo");
		}
		return "";
	}
}
