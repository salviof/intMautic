/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import org.junit.Test;
import static org.junit.Assert.*;
import testes.testesSupers.ServicoRecepcaoOauthTestes;
import testesFW.TesteJUnitBasicoSemPersistencia;

/**
 *
 * @author salvio
 */
public class IntegracaoRestMauticContatoListarComFiltroTest extends TesteJUnitBasicoSemPersistencia {

    public IntegracaoRestMauticContatoListarComFiltroTest() {
    }

    @Test
    public void testSomeMethod() {
        try {

            // TODO review the generated test code and remove the default call to fail.
            SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            ServicoRecepcaoOauthTestes.iniciarServico();

            if (!FabMauticContatoRest.CONTATO_LISTAR_COM_FILTRO.getGestaoToken(SBCore.getUsuarioLogado()).isTemTokemAtivo()) {
                if (FabMauticContatoRest.CONTATO_LISTAR_COM_FILTRO.getGestaoToken(SBCore.getUsuarioLogado()).gerarNovoToken() == null) {

                }
            }
            IntegracaoRestMauticContatoListarComFiltro acao = (IntegracaoRestMauticContatoListarComFiltro) FabMauticContatoRest.CONTATO_LISTAR_COM_FILTRO
                    .getAcao(SBCore.getUsuarioLogado(), "keniavarella@gmail.com");

            ItfRespostaWebServiceSimples resp = acao.getResposta();
            assertNotNull("REsposta nula obtendo lista de contatos ", resp);
            assertTrue("Falha ao obter lista de empresas com filtro" + resp.getRetorno(), resp.isSucesso());
            System.out.println(resp.getRespostaTexto());
            //    System.out.println(resp.getRespostaErro());
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

}
