package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 * Endpoints de e-mails da API REST do Mautic.
 *
 * Três grupos de endpoints, que não devem ser confundidos:
 * <ul>
 * <li>EMAIL_MARKETING_*: e-mail marketing de segmento (emailType "list"),
 * disparado uma vez para os contatos dos segmentos vinculados.</li>
 * <li>MODELO_EMAIL_MARKETING_*: modelo de e-mail marketing (emailType
 * "template"), conteúdo de marketing reutilizável, usado pelo Mautic em
 * campanhas, formulários e automações.</li>
 * <li>MODELO_EMAIL_TRANSIENTE_*: e-mail transacional (transiente), disparado
 * individualmente para um contato a partir de um evento do sistema
 * (confirmação, aviso, lembrete), sem caráter de marketing.</li>
 * </ul>
 * O emailType deve ser fixado na montagem do corpo de cada endpoint.
 *
 * Os endpoints utilizam a API /api (v1), a API /api/v2 do Mautic 7 rejeita
 * tokens client_credentials.
 *
 * @author salvio
 */
@InfoConfigRestClientIntegracao(configuracao = FabConfigModuloMautic.class,
        enderecosDocumentacao = "https://devdocs.mautic.org/en/latest/rest_api/emails.html",
        nomeIntegracao = "mautic",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2
)
public enum FabMauticModeloEmail implements ComoFabricaIntegracaoRest {

    /**
     * Lista e-mails paginados, parâmetro: inicio da página
     */
    @InfoConsumoRestService(getPachServico = "/api/emails?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM + "&orderBy=id&orderByDir=ASC",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"start"},
            adicionarAutenticacaoBearer = true)
    EMAIL_LISTAR,
    /**
     * Detalhes de um e-mail, parâmetro: id do e-mail
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"id"},
            adicionarAutenticacaoBearer = true)
    EMAIL_DETALHES,
    /**
     * Cria um e-mail marketing (emailType "list"), parâmetros: name, subject,
     * customHtml, description, lists (ids dos segmentos), isPublished
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"name", "subject", "customHtml", "description", "lists", "isPublished"}
    )
    EMAIL_MARKETING_CTR_SALVAR_NOVO,
    /**
     * Edita um e-mail marketing, parâmetros: id, name, subject, customHtml,
     * description, lists (ids dos segmentos), isPublished
     *
     * Utiliza PATCH para não sobrescrever os demais campos do e-mail.
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"name", "subject", "customHtml", "description", "lists", "isPublished"}
    )
    EMAIL_MARKETING_CTR_SALVAR_EDITAR,
    /**
     * Envia um e-mail marketing aos contatos dos segmentos vinculados,
     * parâmetro: id do e-mail
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}/send",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"}
    )
    EMAIL_MARKETING_ENVIAR_SEGMENTOS,
    /**
     * Cria um modelo de e-mail marketing (emailType "template"), parâmetros:
     * name, subject, customHtml, description, isPublished
     *
     * Diferente do EMAIL_MARKETING_CTR_SALVAR_NOVO (emailType "list"), o modelo
     * não é vinculado a segmentos nem disparado diretamente: é o conteúdo de
     * marketing reutilizado pelas campanhas e automações do Mautic. Diferente
     * do MODELO_EMAIL_TRANSIENTE_CTR_SALVAR_NOVO, o conteúdo é de marketing, e
     * não uma comunicação transacional disparada por evento do sistema.
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"name", "subject", "customHtml", "description", "isPublished"}
    )
    MODELO_EMAIL_MARKETING_CTR_SALVAR_NOVO,
    /**
     * Edita um modelo de e-mail marketing (emailType "template"), parâmetros:
     * id, name, subject, customHtml, description, isPublished
     *
     * Utiliza PATCH para não sobrescrever os demais campos do e-mail.
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"name", "subject", "customHtml", "description", "isPublished"}
    )
    MODELO_EMAIL_MARKETING_CTR_SALVAR_EDITAR,
    /**
     * Cria um e-mail transacional (transiente), parâmetros: name, subject,
     * customHtml, description, isPublished
     *
     * Comunicação disparada individualmente para um contato a partir de um
     * evento do sistema (ver MODELO_EMAIL_TRANSIENTE_ENVIAR_CONTATO), sem
     * caráter de marketing.
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"name", "subject", "customHtml", "description", "isPublished"}
    )
    MODELO_EMAIL_TRANSIENTE_CTR_SALVAR_NOVO,
    /**
     * Edita um modelo de e-mail transiente, parâmetros: id, name, subject,
     * customHtml, description, isPublished
     *
     * Utiliza PATCH para não sobrescrever os demais campos do e-mail.
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"name", "subject", "customHtml", "description", "isPublished"}
    )
    MODELO_EMAIL_TRANSIENTE_CTR_SALVAR_EDITAR,
    /**
     * Envia um modelo de e-mail transiente a um contato, parâmetros: id do
     * e-mail, id do contato, tokens (Map com os valores de {tokens}
     * substituídos no conteúdo)
     */
    @InfoConsumoRestService(getPachServico = "/api/emails/{0}/contact/{1}/send",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"idEmail", "idContato"},
            parametrosPost = {"tokens"}
    )
    MODELO_EMAIL_TRANSIENTE_ENVIAR_CONTATO;

}
