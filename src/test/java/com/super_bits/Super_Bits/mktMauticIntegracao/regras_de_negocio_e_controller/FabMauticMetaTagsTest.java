/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import testes.testesSupers.TestesApiRest;

/**
 *
 * @author salvio
 */
public class FabMauticMetaTagsTest extends TestesApiRest {

    @Test
    public void testValues() {
        try {
            SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            gerarCodigosChamadasEndpoint(FabMauticMetaTags.class);
        } catch (Throwable t) {
            System.out.println("Deu merda");
        }

    }
}
