package br.org.coletivoJava.integracoes.restMautic.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoHeaderBuilder;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import java.nio.charset.StandardCharsets;

/**
 * Headers padrão das chamadas à API REST do Mautic 7.
 *
 * O Mautic 7 (Symfony 7) só interpreta o corpo como JSON quando o Content-Type
 * application/json é enviado.
 */
public class IntegracaoRestMautic_HeaderPadrao
        extends
        AcaoApiIntegracaoHeaderBuilder {

    public IntegracaoRestMautic_HeaderPadrao(final ItfAcaoApiRest pAcao) {
        super(pAcao);
    }

    @Override
    public void gerarHeaderPadrao() {
        super.gerarHeaderPadrao();
        cabecalho.put("Accept", "application/json");
        // Agente SISTEMA autentica com usuário e senha (Basic), agente USUARIO com OAuth2 (Bearer)
        String tipoAutorizacao = FabTipoAgenteClienteApi.SISTEMA.equals(acao.getTokenGestao().getTipoAgente()) ? "Basic " : "Bearer ";
        cabecalho.put("Authorization", tipoAutorizacao + acao.getTokenGestao().getToken());
        if (acao.isMetodoEnviaDados()) {
            String corpo = acao.gerarCorpoRequisicao();
            if (corpo != null && !corpo.isEmpty()) {
                cabecalho.put("Content-Type", "application/json; charset=UTF-8");
                // O tamanho deve ser em bytes, nomes com acento ocupam mais de um byte em UTF-8
                cabecalho.put("Content-Length", String.valueOf(corpo.getBytes(StandardCharsets.UTF_8).length));
            } else {
                cabecalho.remove("Content-Type");
                cabecalho.remove("Content-Length");
            }
        }

    }

}
