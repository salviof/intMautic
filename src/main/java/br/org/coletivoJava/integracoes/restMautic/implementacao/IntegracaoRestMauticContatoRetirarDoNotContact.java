package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMauticRest;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

@InfoIntegracaoRestMauticRest(tipo = FabMauticContatoRest.CONTATO_RETIRAR_DO_NOT_CONTACT)
public class IntegracaoRestMauticContatoRetirarDoNotContact
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestMauticContatoRetirarDoNotContact(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabMauticContatoRest.CONTATO_RETIRAR_DO_NOT_CONTACT, pTipoAgente,
                pUsuario, pParametro);
    }

    @Override
    public String gerarCorpoRequisicao() {
        return "";
    }

}
