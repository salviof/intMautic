/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import testes.testesSupers.ServicoRecepcaoOauthTestes;
import testesFW.TesteJUnitBasicoSemPersistencia;

/**
 *
 * @author salvio
 */
public class IntegracaoRestMauticContatoListarTest extends TesteJUnitBasicoSemPersistencia {

    public IntegracaoRestMauticContatoListarTest() {
    }

    @Test
    public void testSomeMethod() {
        try {

            // TODO review the generated test code and remove the default call to fail.
            SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            ServicoRecepcaoOauthTestes.iniciarServico();

            if (!FabMauticContatoRest.CONTATO_LISTAR.getGestaoToken(SBCore.getUsuarioLogado()).isTemTokemAtivo()) {
                if (FabMauticContatoRest.CONTATO_LISTAR.getGestaoToken(SBCore.getUsuarioLogado()).gerarNovoToken() == null) {

                }
            }
            IntegracaoRestMauticContatoListar acao = (IntegracaoRestMauticContatoListar) FabMauticContatoRest.CONTATO_LISTAR
                    .getAcao(SBCore.getUsuarioLogado(), 0);

            boolean vazio = false;

            ItfRespostaWebServiceSimples resp = acao.getResposta();
            assertNotNull("REsposta nula obtendo lista de contatos ", resp);
            JsonObject json = resp.getRespostaComoObjetoJson();
            JsonValue contatos = json.get("contacts");
            JsonObject contatosJson = null;
            boolean enviadoComoJsonArray = false;
            try {

                contatosJson = contatos.asJsonObject();

            } catch (ClassCastException t) {

                enviadoComoJsonArray = true;
            }

            if (enviadoComoJsonArray) {
                JsonArray jsonArray = contatos.asJsonArray();
                vazio = jsonArray.isEmpty();
            } else {
                vazio = contatosJson.isEmpty();
            }
            int paginador = 0;

            while (!vazio) {

                for (JsonValue item : contatosJson.values()) {
                    if (item.asJsonObject().containsKey("doNotContact")) {
                        JsonArray naoPertube = item.asJsonObject().getJsonArray("doNotContact");
                        if (!naoPertube.isEmpty()) {

                            JsonObject campos = item.asJsonObject().get("fields").asJsonObject();
                            JsonObject todosCampos = campos.getJsonObject("all");
                            String email = todosCampos.getString("email");
                            for (JsonValue regNaoPertube : naoPertube) {
                                String texto = regNaoPertube.asJsonObject().getString("comments");
                                System.out.println(texto);
                                if (texto.equals("Usuário ou servidor desconhecido")) {
                                    System.out.println("Usuário remoção padrão?" + email);
                                    System.out.println(texto);
                                    int codigoUsuario = todosCampos.getInt("id");
                                    ItfAcaoApiRest acaoRemoverCancelamento = FabMauticContatoRest.CONTATO_RETIRAR_DO_NOT_CONTACT.getAcaoUsuarioLogado(SBCore.getUsuarioLogado(),
                                            codigoUsuario);

                                    ItfRespostaWebServiceSimples resposta = acaoRemoverCancelamento.getResposta();
                                    if (resposta != null && resposta.isSucesso()) {
                                        System.out.println("Não contate removido com sucesso");
                                        System.out.println(email);
                                    } else {
                                        System.out.println("Falha removendo dnc" + email);
                                    }

                                } else {
                                    System.out.println("Usuaŕio removido por motivo diferente" + email);
                                    System.out.println(texto);
                                }
                            }
                        }
                    }
                }
                paginador = paginador + 50;
                acao = (IntegracaoRestMauticContatoListar) FabMauticContatoRest.CONTATO_LISTAR.getAcao(SBCore.getUsuarioLogado(), paginador);
                resp = acao.getResposta();
                json = resp.getRespostaComoObjetoJson();
                if (!json.containsKey("contacts")) {
                    vazio = true;

                } else {
                    contatos = json.get("contacts");
                    enviadoComoJsonArray = false;
                    try {
                        contatosJson = contatos.asJsonObject();

                    } catch (ClassCastException t) {
                        enviadoComoJsonArray = true;
                    }
                    if (enviadoComoJsonArray) {
                        JsonArray jsonArray = contatos.asJsonArray();
                        vazio = jsonArray.isEmpty();
                    } else {
                        vazio = contatosJson.isEmpty();
                    }

                }

            }

            //UtilSBCoreJson.getJsonObjectByTexto(pStringJson);
            assertTrue("Falha ao obter lista de empresas com filtro" + resp.getRetorno(), resp.isSucesso());

            //    System.out.println(resp.getRespostaErro());
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

}
