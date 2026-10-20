package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 * Endpoints de campos personalizados (custom fields) de empresa (company) da
 * API REST do Mautic.
 *
 * Os endpoints utilizam a API /api (v1), a API /api/v2 do Mautic 7 rejeita
 * tokens client_credentials.
 *
 * @author salvio
 */
@InfoConfigRestClientIntegracao(configuracao = FabConfigModuloMautic.class,
        enderecosDocumentacao = "https://devdocs.mautic.org/en/latest/rest_api/fields.html",
        nomeIntegracao = "mautic",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2
)
public enum FabMauticFields implements ComoFabricaIntegracaoRest {

    /**
     * Lista campos de empresa paginados, parâmetro: inicio da página
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM + "&orderBy=id&orderByDir=ASC",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"start"},
            adicionarAutenticacaoBearer = true)
    CAMPO_LISTAR,
    /**
     * Detalhes de um campo de empresa, parâmetro: id do campo
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company/{0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"id"},
            adicionarAutenticacaoBearer = true)
    CAMPO_DETALHES,
    /**
     * Pesquisa um campo de empresa pelo alias, parâmetro: alias do campo
     *
     * O alias é o identificador único do campo no Mautic (nome da coluna no
     * banco). Retorna a mesma estrutura da listagem (fields), com no máximo um
     * campo.
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company?where[0][col]=alias&where[0][expr]=eq&where[0][val]={0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"alias"},
            adicionarAutenticacaoBearer = true)
    CAMPO_DETALHES_POR_ALIAS,
    /**
     * Cria um campo de empresa, parâmetros: label, alias (gerado a partir do
     * label se vazio), type (text, textarea, number, boolean, date, datetime,
     * email, select, multiselect, tel, url...), group (core, social, personal,
     * professional), defaultValue, isRequired, isPublished, properties (opções
     * de select, formato de número etc.)
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"label", "alias", "type", "group", "defaultValue", "isRequired", "isPublished", "properties"}
    )
    CAMPO_CTR_SALVAR_NOVO,
    /**
     * Edita um campo de empresa, parâmetros: id, label, group, defaultValue,
     * isRequired, isPublished, properties
     *
     * Utiliza PATCH para não sobrescrever os demais atributos. O alias e o type
     * não são alterados após a criação, o Mautic usa o alias como coluna no
     * banco.
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"label", "group", "defaultValue", "isRequired", "isPublished", "properties"}
    )
    CAMPO_CTR_SALVAR_EDITAR,
    /**
     * Remove um campo de empresa, parâmetro: id do campo
     *
     * Atenção: os valores gravados nesse campo em todas as empresas são
     * perdidos.
     */
    @InfoConsumoRestService(getPachServico = "/api/fields/company/{0}/delete",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.DELETE,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"}
    )
    CAMPO_CTR_REMOVER;

}
