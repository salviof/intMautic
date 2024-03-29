/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;

import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 *
 * @author SalvioF
 */
@InfoConfigRestClientIntegracao(configuracao = FabConfigModuloMautic.class,
        enderecosDocumentacao = "https://developer.mautic.org/#rest-api",
        nomeIntegracao = "mautic",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2
)
public enum FabMauticContatoRest implements ItfFabricaIntegracaoRest {
//?where[0][col]=email&where[0][expr]={0

    @InfoConsumoRestService(getPachServico = "/api/contacts?start={0}&limit=" + FabMauticContatoRest.QTD_PADRAO_LISTAGEM, tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            adicionarAutenticacaoBearer = true)
    CONTATO_LISTAR,
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/dnc/email/remove", tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true)
    CONTATO_RETIRAR_DO_NOT_CONTACT,
    @InfoConsumoRestService(getPachServico = "/api/contacts?search={0}", tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            adicionarAutenticacaoBearer = true)
    CONTATO_LISTAR_COM_FILTRO,
    /**
     * Encontra uma companha através de um campo, exemplo pelo site.
     */
    @InfoConsumoRestService(getPachServico = "/api/companies?search={0}", tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"search"}, adicionarAutenticacaoBearer = true)
    LISTAREMPRESA_COM_FILTRO,
    CONTATO_CTR_SALVAR_NOVO_CONTATO,
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"}
    )
    CONTATO_CTR_SALVAR_EDITAR_CONTATO,
    ///contacts/ID/activity
    @InfoConsumoRestService(getPachServico = "/api/contacts/{0}/activity",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.GET,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"ID"}
    )
    CONTATO_ATIVIDADES_DO_CONTATO,
    /**
     *
     */
    @InfoConsumoRestService(getPachServico = "/api/companies/new",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.POST,
            adicionarAutenticacaoBearer = true,
            parametrosPost = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"}
    )
    EMPRESA_CTR_SALVAR_NOVA_EMPRESA,
    @InfoConsumoRestService(getPachServico = "/api/companies/{0}/edit",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            tipoConexao = FabTipoConexaoRest.PUT,
            adicionarAutenticacaoBearer = true,
            parametrosGet = {"id"},
            parametrosPost = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"}
    )
    EMPRESA_CTR_SALVAR_EDITAR_EMPRESA,
    @InfoConsumoRestService(getPachServico = "/api/companies/{0}/contact/{1}/add",
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            adicionarAutenticacaoBearer = true,
            tipoConexao = FabTipoConexaoRest.POST,
            parametrosPost = {"id", "email", "firstname", "lastname", "mobile", "tags"}
    )
    EMPRESA_CTR_SALVAR_ADICIONAR_CONTATO;
    public static final String QTD_PADRAO_LISTAGEM = "50";
}
