// This is Groovy Flowstep Version 2.x, running with Groovy runtime 4, Downgrade the script if older behaviour needed.

package script.v2

import com.sap.it.script.v2.api.Message;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.securestore.SecureStoreService;
import com.sap.it.api.securestore.exception.CredentialNotFoundException;

//
// accessSecureStoreService.groovy
// This snipet shows how to access CIS credentials from within a groovy 
// sctript. It has been tested with the latest version of SAP CIS as of
// October, 30th 2026
//
// The original version is from kaushik ganguly 
// https://gist.github.com/kgsnipes
//
// com.sap.it.api
//		Interface ITApi
// https://help.sap.com/doc/471310fc71c94c2d913884e2ff1b4039/Cloud/en-US/com/sap/it/api/ITApi.html
//
// This script will give you an error message with high severity:
// ========================================================================== 
// "You have configured the script with headers or properties that access 
// secure parameters. Avoid accessing secure parameters and assigning them
// to headers or properties."
//
def Message processData(Message message) {
    String password;
    String username;
    def service = ITApiFactory.getApi(SecureStoreService.class, null);

    //Name of secure parameter
    def credential = service.getUserCredential("XXX"); 
    if (credential == null) { 
        throw new CredentialNotFoundException("No credential found for alias 'XXX'");             
    } else {
        username = new String(credential.getUsername());
        password = new String(credential.getPassword());
    }

    //store it in property which can be used in later stage of your integration process.
    message.setProperty("P_Username", username);
    message.setProperty("P_Password", password);
    
    return message;
}
