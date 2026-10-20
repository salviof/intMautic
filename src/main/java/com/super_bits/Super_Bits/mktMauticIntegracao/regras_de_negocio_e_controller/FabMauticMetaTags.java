/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 * Endpoints de Tags e Estágios (stages) da API REST do Mautic.
 *
 * Os endpoints utilizam a API /api (v1), a API /api/v2 do Mautic 7 rejeita
 * tokens client_credentials.
 *
 * @author salvio
 */
@InfoConfigRestClientIntegracao(configuracao = FabConfigModuloMautic.class,
        enderecosDocumentacao = "https://devdocs.mautic.org/en/latest/rest_api/",
        nomeIntegracao = "mautic",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2
)
public enum FabMauticMetaTags implements ComoFabricaIntegracaoRest {

    /**
     * Lista tags paginadas, parâmetro: inicio da página
     */
    @InfoConsumoRestService(getPachServico = "/api/tags?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM + "&orderBy=id&orderByDir=ASC",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"start"},
            adicionarAutenticacaoBearer = true)
    TAG_LISTAR,
    /**
     * Detalhes de uma tag, parâmetro: id da tag
     */
    @InfoConsumoRestService(getPachServico = "/api/tags/{0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"id"},
            adicionarAutenticacaoBearer = true)
    TAG_DETALHES,
    /**
     * Cria uma tag, parâmetros: tag (nome da tag), description
     */
    @InfoConsumoRestService(getPachServico = "/api/tags/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"tag", "description"}
    )
    TAG_CTR_SALVAR_NOVA_TAG,
    /**
     * Edita uma tag, parâmetros: id, tag (nome da tag), description
     */
    @InfoConsumoRestService(getPachServico = "/api/tags/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"tag", "description"}
    )
    TAG_CTR_SALVAR_EDITAR_TAG,
    /**
     * Atribui tags a um contato, parâmetros: id do contato, ids das tags
     * (Collection ou array de ids, ou um único id)
     *
     * Utiliza PATCH para não sobrescrever os demais campos do contato, as tags
     * já existentes no contato são mantidas. O Mautic identifica as tags deste
     * endpoint pelo nome, os ids são convertidos em nomes na montagem do corpo
     * da requisição.
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"tags"}
    )
    ATRIBUIR_TAG_CONTATO,
    /**
     * Retira tags de um contato, parâmetros: id do contato, tags
     * (List&lt;String&gt;)
     *
     * O Mautic remove a tag quando o nome é enviado com o prefixo "-" (ex:
     * "-cliente"), o prefixo deve ser adicionado na montagem do corpo da
     * requisição.
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"tags"}
    )
    RETIRAR_TAG_CONTATO,
    /**
     * Lista estágios (stages) paginados, parâmetro: inicio da página
     */
    @InfoConsumoRestService(getPachServico = "/api/stages?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM + "&orderBy=id&orderByDir=ASC",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"start"},
            adicionarAutenticacaoBearer = true)
    ESTAGIO_LISTAR,
    @InfoConsumoRestService(getPachServico = "/api/stages?where[0][col]=weight&where[0][expr]=eq&where[0][val]={0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"peso"},
            adicionarAutenticacaoBearer = true)
    ESTAGIO_POR_PESO,
    /**
     * Detalhes de um estágio (stage), parâmetro: id do estágio
     */
    @InfoConsumoRestService(getPachServico = "/api/stages/{0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"id"},
            adicionarAutenticacaoBearer = true)
    ESTAGIO_DETALHES,
    /**
     * Edita um estágio (stage), parâmetros: id, name, description, weight,
     * isPublished
     */
    @InfoConsumoRestService(getPachServico = "/api/stages/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"name", "description", "weight", "isPublished"}
    )
    ESTAGIO_CTR_SALVAR_EDITAR_ESTAGIO,
    @InfoConsumoRestService(getPachServico = "/api/stages/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"name", "description", "weight", "isPublished"}
    )
    ESTAGIO_CTR_NOVO_ESTAGIO,
    /**
     * Vincula um contato a um estágio (stage), parâmetros: id do estágio, id do
     * contato
     */
    @InfoConsumoRestService(getPachServico = "/api/stages/{0}/contact/{1}/add",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"idEstagio", "idContato"}
    )
    VINCULAR_ESTAGIO_CONTATO;

}
