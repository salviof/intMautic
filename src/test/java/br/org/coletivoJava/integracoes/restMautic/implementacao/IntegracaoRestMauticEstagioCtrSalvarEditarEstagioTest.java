/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticMetaTags;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author salvio
 */
public class IntegracaoRestMauticEstagioCtrSalvarEditarEstagioTest {

    public IntegracaoRestMauticEstagioCtrSalvarEditarEstagioTest() {
    }

    /**
     * Test of gerarCorpoRequisicao method, of class
     * IntegracaoRestMauticEstagioCtrSalvarEditarEstagio.
     */
    @Test
    public void testGerarCorpoRequisicao() {
        SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        ItfRespostaWebServiceSimples resp = FabMauticMetaTags.ESTAGIO_CTR_SALVAR_EDITAR_ESTAGIO.getAcao(2, "Etapa Teste2", "Nao informada", 1, true).getResposta();
        System.out.println(resp.getRespostaTexto());
        assertTrue("Falha atualizando", resp.isSucesso());
    }

}
