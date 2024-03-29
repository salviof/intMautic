/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.testes;

import testes.spark.ServidorOauthRecepcaoSpark;
import com.super_bits.Super_Bits.mktMauticIntegracao.configAppp.ConfiguradorCoremktMauticIntegracao;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author sfurbino
 * @since 17/12/2019
 * @version 1.0
 */
public class TesteUnitarioOauth {

    @Test
    public void testeMauticOauth() {
        SBCore.configurar(new ConfiguradorCoremktMauticIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);

        ItfTokenGestao oauth = FabMauticContatoRest.CONTATO_CTR_SALVAR_EDITAR_CONTATO.getGestaoToken(SBCore.getUsuarioLogado());
        String url = oauth.getComoGestaoOauth().getUrlObterCodigoSolicitacao();
        String urlRetorno = oauth.getComoGestaoOauth().getUrlRetornoReceberCodigoSolicitacao();

        if (oauth.isTemTokemAtivo()) {
            System.out.println("Já tem token");
            System.out.println(oauth.getToken());
        } else {

            try {
                URL urlanalize = new URL(urlRetorno);
                String patch = urlanalize.getPath();
                ServidorOauthRecepcaoSpark servidor = new ServidorOauthRecepcaoSpark(oauth.getComoGestaoOauth(), 7666, patch);
                servidor.start();
            } catch (MalformedURLException t) {

            }

            try {
                Runtime.getRuntime().exec(new String[]{"google-chrome-stable", url});
            } catch (IOException ex) {
                Logger.getLogger(TesteUnitarioOauth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int segundos = 10000;
        while (segundos >= 0) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteUnitarioOauth.class.getName()).log(Level.SEVERE, null, ex);
            }
            segundos--;
        }

    }

}
