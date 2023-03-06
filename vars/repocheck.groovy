import java.net.URL
import java.net.HttpURLConnection 
def gitHubUrl = "https://github.com/CrasisRS/for_testing_only.git"
def timeoutSeconds = 60
def intervalSeconds = 5
def call(){
def startTime = System.currentTimeMillis() println "Checking connection to GH repo ${gitHubUrl}" 
while ((System.currentTimeMillis() - startTime) / 1000 < timeoutSeconds) {
    try {
        def connection = new URL(gitHubUrl).openConnection() as HttpURLConnection
        connection.setRequestMethod("HEAD")
        if ( connection.repsonseCode == HttpURLConnection.HTTP_OK) {
            println "Connection successful!"
            return true
        }
    } catch (Exception e) {
        //Ignore
    }
    println "Connection unsuccesful. REtrying in ${intervalSeconds} seconds"
    Threat.sleep(intervalSeconds * 1000)
} println "Connection unsuccessful after ${timeoutSeconds} seconds."
return false
}
