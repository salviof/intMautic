package br.org.coletivoJava.integracoes.restMautic.implementacao;

import br.org.coletivoJava.integracoes.restMautic.api.InfoIntegracaoRestMautic;
import com.super_bits.Super_Bits.mktMauticIntegracao.regras_de_negocio_e_controller.FabMauticContatoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

@InfoIntegracaoRestMautic(tipo = FabMauticContatoRest.CONTATO_LISTAR_COM_FILTRO)
public class IntegracaoRestMauticContatoListarComFiltro
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestMauticContatoListarComFiltro(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ComoUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabMauticContatoRest.CONTATO_LISTAR_COM_FILTRO, pTipoAgente,
                pUsuario, pParametro);
    }

    @Override
    public String gerarUrlRequisicao() {
        return super.gerarUrlRequisicao(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
