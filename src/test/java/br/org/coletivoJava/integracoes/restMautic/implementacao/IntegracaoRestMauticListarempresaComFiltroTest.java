/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import org.junit.Test;
import static org.junit.Assert.*;
import testes.testesSupers.ServicoRecepcaoOauthTestes;
import testesFW.TesteJUnitBasicoSemPersistencia;

/**
 *
 * @author sfurbino
 */
public class IntegracaoRestMauticListarempresaComFiltroTest extends TesteJUnitBasicoSemPersistencia {

    public IntegracaoRestMauticListarempresaComFiltroTest() {
    }

    @Test
    public void testSomeMethod() {
        try {

            // TODO review the generated test code and remove the default call to fail.
            SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            ServicoRecepcaoOauthTestes.iniciarServico();

            if (!FabMauticContatoRest.LISTAREMPRESA_COM_FILTRO.getGestaoToken(SBCore.getUsuarioLogado()).isTemTokemAtivo()) {
                if (FabMauticContatoRest.LISTAREMPRESA_COM_FILTRO.getGestaoToken(SBCore.getUsuarioLogado()).gerarNovoToken() == null) {

                }
            }
            IntegracaoRestMauticListarempresaComFiltro acao = (IntegracaoRestMauticListarempresaComFiltro) FabMauticContatoRest.LISTAREMPRESA_COM_FILTRO.getAcao(SBCore.getUsuarioLogado(), "salviof@gmail.com");

            ItfRespostaWebServiceSimples resp = acao.getResposta();
            assertTrue("Falha ao obter lista de empresas com filtro", resp.isSucesso());
            System.out.println(resp.getRespostaTexto());
            //    System.out.println(resp.getRespostaErro());
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

}
