package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMautic;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@InfoIntegracaoRestMautic(tipo = FabMauticContatoRest.CONTATO_CTR_SALVAR_EDITAR_CONTATO)
public class IntegracaoRestMauticContatoCtrSalvarEditarContato
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestMauticContatoCtrSalvarEditarContato(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabMauticContatoRest.CONTATO_CTR_SALVAR_EDITAR_CONTATO,
                pTipoAgente, pUsuario, pParametro);
    }

    @Override
    public String gerarCorpoRequisicao() {
        List<String> tags = (List) parametros.get(5);
        JSONObject jsonContato = new JSONObject();
        jsonContato.put("id", parametros.get(0));
        jsonContato.put("email", parametros.get(1));
        jsonContato.put("firstname", parametros.get(2));
        jsonContato.put("lastname", parametros.get(3));
        jsonContato.put("mobile", parametros.get(4));

        if (!tags.isEmpty()) {
            JSONArray jsonTags = new JSONArray();
            for (String tag : tags) {
                //JSONObject jsonTag = new JSONObject();
                // jsonTag.put("id", "??IDTAG");
                //jsonTag.put("tag", tag);
                //jsonTags.add(jsonTag);
                jsonTags.add(tag);

            }

            jsonContato.put("tags", jsonTags);
        }

        String corpoResicao = jsonContato.toJSONString();
        // return "{\"id\":\"" + parametros.get(0] + "\",\"email\":\"" + parametros.get(1] + "\",\"firstname\":\"" + parametros.get(2]
        //        + "\",\"lastname\":\"" + parametros.get(3] + "\",\"mobile\":\"" + parametros.get(4] + "\"}";
        return corpoResicao;
    }

}
