package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabConfigModuloMautic;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.GestaoTokenOath2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.TokenDeAcessoExternoSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import jakarta.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Gestão do token OAuth2 do Mautic (testado com Mautic 7.2).
 *
 * Endpoints: /oauth/v2/authorize e /oauth/v2/token, os parâmetros do token são
 * enviados como application/x-www-form-urlencoded.
 *
 * Agente USUARIO: grant authorization_code (exige autorização no navegador).
 * Agente SISTEMA: Basic Auth com usuário e senha (USUARIO_ADMIN e SENHA_ADMIN
 * de FabConfigModuloMautic), sem navegador. A autenticação básica deve estar
 * habilitada nas configurações de API do Mautic.
 *
 * @see
 * <a href="https://devdocs.mautic.org/en/latest/rest_api/authentication.html">Autenticação
 * API Mautic</a>
 */
public class GestaoTokenRestMautic extends GestaoTokenOath2 {

    private static final String PATH_AUTORIZACAO = "/oauth/v2/authorize";
    private static final String PATH_TOKEN = "/oauth/v2/token";

    public GestaoTokenRestMautic(final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario) {
        super(FabMauticContatoRest.class, pTipoAgente, pUsuario);

    }

    private static String codificar(String pValor) {
        if (pValor == null) {
            return "";
        }
        try {
            return URLEncoder.encode(pValor, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            return pValor;
        }
    }

    private static Map<String, String> gerarCabecalhoFormulario() {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("Content-Type", "application/x-www-form-urlencoded");
        cabecalho.put("Accept", "application/json");
        return cabecalho;
    }

    @Override
    public String gerarUrlRetornoSucessoGeracaoTokenDeAcesso() {

        return configuracoesAmbiente.getPropriedade(FabConfigModuloMautic.URL_SERIVIDOR_CLIENTE);

    }

    @Override
    public String gerarUrlAutenticaoObterCodigoSolicitacaoToken() {
        return urlServidorApiRest
                + PATH_AUTORIZACAO
                + "?response_type=code"
                + "&grant_type=authorization_code"
                + "&client_id=" + codificar(chavePublica)
                + "&redirect_uri=" + codificar(urlRetornoReceberCodigoSolicitacao);
    }

    @Override
    public InfoTokenOauth2 extrairToken(JsonObject pJson) {
        if (pJson == null || !pJson.containsKey("access_token")) {
            return null;
        }
        InfoTokenOauth2 tokenGerado = new InfoTokenOauth2(pJson.getString("access_token"));
        tokenGerado.setTokenRefresh(pJson.getString("refresh_token", null));
        String expiraStr = pJson.getString("dataHoraExpirarToken", null);
        if (expiraStr != null) {
            tokenGerado.setDataHoraExpirarToken(new Date(Long.valueOf(expiraStr)));
        }
        return tokenGerado;
    }

    @Override
    public boolean armazenarRespostaToken(String pJson) {

        JSONParser parser = new JSONParser();
        JSONObject respostaJson;
        try {
            respostaJson = (JSONObject) parser.parse(pJson);
            if (respostaJson.get("expires_in") != null) {
                Date dataHora = UtilCRCDataHora.incrementaSegundos(new Date(), Integer.parseInt(respostaJson.get("expires_in").toString()));
                respostaJson.put("dataHoraExpirarToken", String.valueOf(dataHora.getTime()));
            }
            if (respostaJson.get("refresh_token") == null) {
                // Mantém o refresh_token anterior caso o servidor não envie um novo
                JsonObject tokenArmazenado = loadTokenArmazenadoComoJsonObject();
                if (tokenArmazenado != null && tokenArmazenado.containsKey("refresh_token")) {
                    respostaJson.put("refresh_token", tokenArmazenado.getString("refresh_token"));
                }
            }

            return super.armazenarRespostaToken(respostaJson.toJSONString()); //chamada super do metodo (implementação classe pai)
        } catch (ParseException ex) {
            Logger.getLogger(GestaoTokenRestMautic.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean isAgenteSistema() {
        return FabTipoAgenteClienteApi.SISTEMA.equals(tipoAgente);
    }

    @Override
    public boolean isCodigoSolicitacaoRegistrado() {
        if (isAgenteSistema()) {
            // A autenticação por usuário e senha não utiliza código de solicitação, evita abrir o navegador
            return true;
        }
        return super.isCodigoSolicitacaoRegistrado();
    }

    @Override
    public ItfTokenDeAcessoExterno loadTokenArmazenado() {
        if (isAgenteSistema()) {
            // A credencial vem da configuração, não é armazenada em arquivo
            return gerarNovoTokenUsuarioSenha();
        }
        return super.loadTokenArmazenado();
    }

    @Override
    public InfoTokenOauth2 gerarNovoToken() {
        if (isAgenteSistema()) {
            return gerarNovoTokenUsuarioSenha();
        }
        return super.gerarNovoToken();
    }

    /**
     * Gera a credencial Basic Auth (base64 de usuario:senha) utilizada pelo
     * agente SISTEMA, a autenticação básica deve estar habilitada nas
     * configurações de API do Mautic.
     *
     * @return credencial Basic Auth, ou null caso usuário/senha não estejam
     * configurados
     */
    private InfoTokenOauth2 gerarNovoTokenUsuarioSenha() {
        String usuarioAdmin = configuracoesAmbiente.getPropriedade(FabConfigModuloMautic.USUARIO_ADMIN);
        String senhaAdmin = configuracoesAmbiente.getPropriedade(FabConfigModuloMautic.SENHA_ADMIN);
        if (UtilCRCStringValidador.isNuloOuEmbranco(usuarioAdmin) || UtilCRCStringValidador.isNuloOuEmbranco(senhaAdmin)) {
            Logger.getLogger(GestaoTokenRestMautic.class.getName()).log(Level.SEVERE,
                    "Usuário e senha do agente SISTEMA não configurados em {0}", FabConfigModuloMautic.class.getSimpleName());
            setToken(null);
            return null;
        }
        String credencial = Base64.getEncoder().encodeToString((usuarioAdmin + ":" + senhaAdmin).getBytes(StandardCharsets.UTF_8));
        /// por compatibilidade com os 2 tokens, devido a anotação  tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2 q espera um token deste tipo
        setToken(new InfoTokenOauth2(credencial));
        return getTokenCompleto();
    }

    @Override
    public boolean isTemTokemAtivo() {
        if (super.isTemTokemAtivo()) {
            return true;
        }
        // Token expirado: tenta renovar com o refresh_token antes de exigir nova autorização
        if (getTokenCompleto() != null && getTokenCompleto().getTokenRefresh() != null) {
            renovarToken();
            return super.isTemTokemAtivo();
        }
        return false;
    }

    @Override
    public InfoTokenOauth2 renovarToken() {
        if (isAgenteSistema()) {
            return gerarNovoTokenUsuarioSenha();
        }
        JsonObject tokenArqmazenadoJson = loadTokenArmazenadoComoJsonObject();
        if (tokenArqmazenadoJson == null || !tokenArqmazenadoJson.containsKey("refresh_token")) {
            return getTokenCompleto();
        }

        String tokenAtualizacao = tokenArqmazenadoJson.getString("refresh_token");
        ChamadaHttpSimples renovacaoToken = new ChamadaHttpSimples();
        renovacaoToken.setTipoConexao(FabTipoConexaoRest.POST);
        renovacaoToken.setEnderecoHost(urlServidorApiRest);
        renovacaoToken.setPath(PATH_TOKEN);
        renovacaoToken.setCabecalhos(gerarCabecalhoFormulario());
        renovacaoToken.setCorpo("grant_type=refresh_token"
                + "&client_id=" + codificar(chavePublica)
                + "&client_secret=" + codificar(chavePrivada)
                + "&refresh_token=" + codificar(tokenAtualizacao));

        RespostaWebServiceSimples resp = UtilSBApiRestClient.getRespostaRest(renovacaoToken);

        if (resp != null && resp.isSucesso()) {
            JsonObject respostaJson = resp.getRespostaComoObjetoJson();

            armazenarRespostaToken(UtilCRCJson.getTextoByJsonObjeect(respostaJson));
            loadTokenArmazenado();
        }
        return getTokenCompleto();

    }

    @Override
    public boolean validarToken() {
        if (getTokenCompleto() == null) {
            return false;
        }
        return isTemTokemAtivo();
    }

    @Override
    protected ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso() {
        if (codigoSolicitacao == null) {
            throw new UnsupportedOperationException("O código de acesso para obter a chave não foi definido");
        }
        String texto = "grant_type=authorization_code"
                + "&client_id=" + codificar(chavePublica)
                + "&client_secret=" + codificar(chavePrivada)
                + "&code=" + codificar(codigoSolicitacao)
                + "&redirect_uri=" + codificar(urlRetornoReceberCodigoSolicitacao);
        chamadaObterChaveDeAcesso = new ChamadaHttpSimples();

        chamadaObterChaveDeAcesso.setPath(PATH_TOKEN);
        chamadaObterChaveDeAcesso.setTipoConexao(FabTipoConexaoRest.POST);
        chamadaObterChaveDeAcesso.setCorpo(texto);
        chamadaObterChaveDeAcesso.setCabecalhos(gerarCabecalhoFormulario());
        chamadaObterChaveDeAcesso.setEnderecoHost(urlServidorApiRest);
        return chamadaObterChaveDeAcesso;
    }

}
