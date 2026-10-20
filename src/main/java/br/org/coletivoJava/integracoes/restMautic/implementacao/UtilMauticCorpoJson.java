package br.org.coletivoJava.integracoes.restMautic.implementacao;

import java.util.Collection;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Montagem do corpo JSON enviado para a API REST do Mautic 7.
 *
 * Campos nulos não são enviados, para não sobrescrever valores existentes no
 * Mautic.
 */
final class UtilMauticCorpoJson {

    private static final String[] CAMPOS_CONTATO = {"email", "firstname", "lastname", "mobile"};
    private static final String[] CAMPOS_EMPRESA = {"companyname", "companyemail", "companywebsite", "companyphone", "companydescription"};

    private UtilMauticCorpoJson() {
    }

    private static Object getParametro(List<Object> pParametros, int pIndice) {
        if (pParametros == null || pParametros.size() <= pIndice) {
            return null;
        }
        return pParametros.get(pIndice);
    }

    private static JSONObject gerarJson(List<Object> pParametros, int pIndiceInicial, String[] pCampos) {
        JSONObject json = new JSONObject();
        for (int i = 0; i < pCampos.length; i++) {
            Object valor = getParametro(pParametros, pIndiceInicial + i);
            if (valor != null) {
                json.put(pCampos[i], String.valueOf(valor));
            }
        }
        return json;
    }

    /**
     * @param pParametros parâmetros da ação
     * @param pIndiceInicial indice do parâmetro email, seguido de firstname,
     * lastname, mobile e tags (opcional)
     * @return json do contato
     */
    static String gerarCorpoContato(List<Object> pParametros, int pIndiceInicial) {
        JSONObject jsonContato = gerarJson(pParametros, pIndiceInicial, CAMPOS_CONTATO);
        Object tags = getParametro(pParametros, pIndiceInicial + CAMPOS_CONTATO.length);
        if (tags instanceof Collection && !((Collection) tags).isEmpty()) {
            JSONArray jsonTags = new JSONArray();
            for (Object tag : (Collection) tags) {
                jsonTags.add(String.valueOf(tag));
            }
            jsonContato.put("tags", jsonTags);
        }
        return jsonContato.toJSONString();
    }

    /**
     * @param pParametros parâmetros da ação
     * @param pIndiceInicial indice do parâmetro companyname, seguido de
     * companyemail, companywebsite, companyphone e companydescription
     * @return json da empresa
     */
    static String gerarCorpoEmpresa(List<Object> pParametros, int pIndiceInicial) {
        return gerarJson(pParametros, pIndiceInicial, CAMPOS_EMPRESA).toJSONString();
    }

}
