package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticTags;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticMetaTags;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import java.util.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMauticTags(tipo = FabMauticMetaTags.RETIRAR_TAG_CONTATO)
public class IntegracaoRestMauticRetirarTagContato
		extends
			AcaoApiIntegracaoComOauthAbstrato {

	public IntegracaoRestMauticRetirarTagContato(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
		super(FabMauticMetaTags.RETIRAR_TAG_CONTATO, pTipoAgente, pUsuario,
				pParametro);
	}

	/**
	 * Parâmetros: id do contato, tags (Collection&lt;String&gt;)
	 *
	 * O Mautic remove a tag do contato quando o nome é enviado com o prefixo
	 * "-".
	 */
	@Override
	public String gerarCorpoRequisicao() {
		if (parametros.isEmpty() || parametros.get(0) == null
				|| parametros.get(0).toString().trim().isEmpty()) {
			throw new UnsupportedOperationException("Informe o parâmetro id do contato");
		}
		if (parametros.size() <= 1 || !(parametros.get(1) instanceof Collection)
				|| ((Collection) parametros.get(1)).isEmpty()) {
			throw new UnsupportedOperationException("Informe ao menos uma tag");
		}
		JSONArray jsonTags = new JSONArray();
		for (Object tag : (Collection) parametros.get(1)) {
			if (tag != null) {
				jsonTags.add("-" + tag);
			}
		}
		JSONObject json = new JSONObject();
		json.put("tags", jsonTags);
		return json.toJSONString();
	}
}
