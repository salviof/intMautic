/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;

import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 *
 * @author SalvioF
 */
/**
 * Endpoints da API REST do Mautic, compatível com Mautic 7.x.
 *
 * Os endpoints utilizam a API /api (v1), a API /api/v2 do Mautic 7 rejeita
 * tokens client_credentials.
 *
 * @author SalvioF
 */
@InfoConfigRestClientIntegracao(configuracao = FabConfigModuloMautic.class,
        enderecosDocumentacao = "https://devdocs.mautic.org/en/latest/rest_api/",
        nomeIntegracao = "mautic",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2
)
public enum FabMauticContatoRest implements ComoFabricaIntegracaoRest {

    /**
     * Lista contatos paginados, parâmetro: inicio da página
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM + "&orderBy=id&orderByDir=ASC",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"start"},
            adicionarAutenticacaoBearer = true)
    CONTATO_LISTAR,
    /**
     * Remove o não contate do canal email, parâmetro: id do contato
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/dnc/email/remove",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            parametrosGet = {"id"},
            adicionarAutenticacaoBearer = true)
    CONTATO_RETIRAR_DO_NOT_CONTACT,
    /**
     * Pesquisa contatos, parâmetro: termo de pesquisa (ex: email)
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts?search={0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"search"},
            adicionarAutenticacaoBearer = true)
    CONTATO_LISTAR_COM_FILTRO,
    /**
     * Encontra uma companha através de um campo, exemplo pelo site.
     */
    @InfoConsumoRestService(getPachServico = "/api/companies?search={0}",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            parametrosGet = {"search"}, adicionarAutenticacaoBearer = true)
    LISTAREMPRESA_COM_FILTRO,
    /**
     * Cria um contato, parâmetros: email, firstname, lastname, mobile, tags
     * (opcional, List&lt;String&gt;)
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"email", "firstname", "lastname", "mobile", "tags"}
    )
    CONTATO_CTR_SALVAR_NOVO_CONTATO,
    /**
     * Edita um contato, parâmetros: id, email, firstname, lastname, mobile,
     * tags (List&lt;String&gt;)
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"email", "firstname", "lastname", "mobile", "tags"}
    )
    CONTATO_CTR_SALVAR_EDITAR_CONTATO,
    /**
     * Atividades do contato, parâmetro: id do contato
     */
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/activity",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"}
    )
    CONTATO_ATIVIDADES_DO_CONTATO,
    /**
     * Cria uma empresa, parâmetros: companyname, companyemail, companywebsite,
     * companyphone, companydescription
     */
    @InfoConsumoRestService(getPachServico = "/api/companies/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"}
    )
    EMPRESA_CTR_SALVAR_NOVA_EMPRESA,
    /**
     * Edita uma empresa, parâmetros: id, companyname, companyemail,
     * companywebsite, companyphone, companydescription
     */
    @InfoConsumoRestService(getPachServico = "/api/companies/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"}
    )
    EMPRESA_CTR_SALVAR_EDITAR_EMPRESA,
    /**
     * Adiciona um contato à empresa, parâmetros: id da empresa, id do contato
     */
    @InfoConsumoRestService(getPachServico = "/api/companies/{0}/contact/{1}/add",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            adicionarAutenticacaoBearer = true,
            tipoConexao = FabTipoConexaoRest.POST,
            parametrosGet = {"idEmpresa", "idContato"}
    )
    EMPRESA_CTR_SALVAR_ADICIONAR_CONTATO,
    /**
     * Atualiza campos da empresa, incluindo os campos personalizados (dados
     * dinâmicos), parâmetros: id da empresa, JsonObject com os valores
     * atualizados, indexados pelo alias do campo no Mautic (ex.:
     * {"companyname": "Padaria Central", "cc_segmento_empresa": "varejo"})
     *
     * Utiliza PATCH, apenas os campos enviados são alterados.
     */
    @InfoConsumoRestService(getPachServico = "/api/companies/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoInformacaoEnviada = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PATCH,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"idEmpresa"},
            parametrosPost = {"valores"}
    )
    ATUALIZAR_DADOS_DINAMICOS;
    public static final String QTD_PADRAO_LISTAGEM = "50";
}
