package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMautic;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

@InfoIntegracaoRestMautic(tipo = FabMauticContatoRest.EMPRESA_CTR_SALVAR_EDITAR_EMPRESA)
public class IntegracaoRestMauticEmpresaCtrSalvarEditarEmpresa
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestMauticEmpresaCtrSalvarEditarEmpresa(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabMauticContatoRest.EMPRESA_CTR_SALVAR_EDITAR_EMPRESA,
                pTipoAgente, pUsuario, pParametro);
    }

    /**
     * Parâmetros: id, companyname, companyemail, companywebsite, companyphone,
     * companydescription
     */
    @Override
    public String gerarCorpoRequisicao() {
        return UtilMauticCorpoJson.gerarCorpoEmpresa(parametros, 1);
    }

}
