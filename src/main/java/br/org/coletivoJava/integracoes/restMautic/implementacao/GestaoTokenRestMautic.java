package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabConfigModuloMautic;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.GestaoTokenOath2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import jakarta.json.JsonObject;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GestaoTokenRestMautic extends GestaoTokenOath2 {

    public GestaoTokenRestMautic(final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario) {
        super(FabMauticContatoRest.class, pTipoAgente, pUsuario);

    }

    @Override
    public String gerarUrlRetornoSucessoGeracaoTokenDeAcesso() {

        return configuracoesAmbiente.getPropriedade(FabConfigModuloMautic.URL_SERIVIDOR_CLIENTE);

    }

    @Override
    public String gerarUrlAutenticaoObterCodigoSolicitacaoToken() {
        return urlServidorApiRest
                + "/oauth/v2/authorize"
                + "?response_type=code&client_id=" + chavePublica
                + "&redirect_uri=" + urlRetornoReceberCodigoSolicitacao;
    }

    @Override
    public InfoTokenOauth2 extrairToken(JsonObject pJson) {
        String tk = (String) pJson.getString("access_token");
        InfoTokenOauth2 tokenGerado = new InfoTokenOauth2(tk);
        tokenGerado.setTokenRefresh((String) pJson.getString("refresh_token"));
        String expiraStr = String.valueOf(pJson.getString("dataHoraExpirarToken"));
        tokenGerado.setDataHoraExpirarToken(new Date(Long.valueOf(expiraStr)));
        return tokenGerado;
    }

    @Override
    public boolean armazenarRespostaToken(String pJson) {

        JSONParser parser = new JSONParser();
        JSONObject respostaJson;
        try {
            respostaJson = (JSONObject) parser.parse(pJson);
            Date dataHora = UtilCRCDataHora.incrementaSegundos(new Date(), Integer.parseInt(respostaJson.get("expires_in").toString()));
            respostaJson.put("dataHoraExpirarToken", String.valueOf(dataHora.getTime()));

            return super.armazenarRespostaToken(respostaJson.toJSONString()); //chamada super do metodo (implementação classe pai)
        } catch (ParseException ex) {
            Logger.getLogger(GestaoTokenRestMautic.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    @Override
    public InfoTokenOauth2 gerarNovoToken() {
        return super.gerarNovoToken(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public InfoTokenOauth2 renovarToken() {
        JsonObject tokenArqmazenadoJson = loadTokenArmazenadoComoJsonObject();
        if (tokenArqmazenadoJson == null) {
            return getTokenCompleto();
        }

        String tokenAtualizacao = tokenArqmazenadoJson.getString("refresh_token");
        ChamadaHttpSimples renovacaoToken = new ChamadaHttpSimples();
        renovacaoToken.setTipoConexao(FabTipoConexaoRest.POST);
        renovacaoToken.setEnderecoHost(urlServidorApiRest);
        renovacaoToken.setPath("/oauth/v2/token");
        renovacaoToken.setCabecalhos(new HashMap<>());
        renovacaoToken.setCorpo("client_id=" + chavePublica
                + "&client_secret=" + chavePrivada
                + "&grant_type=refresh_token"
                + "&refresh_token=" + tokenAtualizacao
                + "&redirect_uri=" + urlRetornoReceberCodigoSolicitacao);

        RespostaWebServiceSimples resp = UtilSBApiRestClient.getRespostaRest(renovacaoToken);

        if (resp.isSucesso()) {
            JsonObject respostaJson = resp.getRespostaComoObjetoJson();

            armazenarRespostaToken(UtilCRCJson.getTextoByJsonObjeect(respostaJson));
            loadTokenArmazenado();
            return getTokenCompleto();
        }
        return getTokenCompleto();

    }

    @Override
    public boolean validarToken() {
        //TODO implementar Validação
        if (getTokenCompleto() == null) {
            return false;
        }
        if (!getTokenCompleto().isTokenValido()) {
            return renovarToken().isTokenValido();

        }
        return getTokenCompleto().isTokenValido();
    }

    @Override
    protected ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso() {
        if (codigoSolicitacao == null) {
            throw new UnsupportedOperationException("O código de acesso para obter a chave não foi definido");
        }
        String texto = "client_id=" + chavePublica
                + "&client_secret=" + chavePrivada
                + "&grant_type=authorization_code"
                + "&code=" + codigoSolicitacao
                + "&redirect_uri=" + urlRetornoReceberCodigoSolicitacao;
        chamadaObterChaveDeAcesso = new ChamadaHttpSimples();

        chamadaObterChaveDeAcesso.setPath("/oauth/v2/token");
        chamadaObterChaveDeAcesso.setTipoConexao(FabTipoConexaoRest.POST);
        chamadaObterChaveDeAcesso.setCorpo(texto);
        chamadaObterChaveDeAcesso.setCabecalhos(new HashMap<>());
        chamadaObterChaveDeAcesso.setEnderecoHost(urlServidorApiRest);
        return chamadaObterChaveDeAcesso;
    }

}
