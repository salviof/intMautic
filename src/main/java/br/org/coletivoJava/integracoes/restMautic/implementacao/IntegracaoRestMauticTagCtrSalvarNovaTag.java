package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticTags;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticMetaTags;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticTags(tipo = FabMauticMetaTags.TAG_CTR_SALVAR_NOVA_TAG)
public class IntegracaoRestMauticTagCtrSalvarNovaTag
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticTagCtrSalvarNovaTag(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticMetaTags.TAG_CTR_SALVAR_NOVA_TAG, pTipoAgente, pUsuario,
				pParametro);
	}

	/**
	 * Parâmetros: tag, description
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro tag");
		}
		String[] campos = {"tag", "description"};
		JSONObject json = new JSONObject();
		for (int i = 0; i < campos.length; i++) {
			int indice = 0 + i;
			if (parametros.size() > indice && parametros.get(indice) != null) {
				json.put(campos[i], parametros.get(indice));
			}
		}
		return json.toJSONString();
	}
}
