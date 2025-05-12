import groovy.json.JsonSlurper
import java.io.File

/*def ocGetPodCommand = "oc get pod/my-quarkus-app-run-build-xb6q4-build-sign-image-pod -n  my-quarkus-app-dev -o json"

def proc = ocGetPodCommand.execute()
def b = new StringBuffer()
proc.consumeProcessErrorStream(b)

println proc.text
println b.toString()
*/

def namespace = "cm-1"


String fileContents = new File("./temp/pod-data.json").text

println(fileContents)

def jsonSlurperComponentTemplates = new JsonSlurper()
def slurpedData=jsonSlurperComponentTemplates.parseText(fileContents)

slurpedData.spec.containers.each {  container ->

  //println(container.name)
  //println(container.image)
  configMapData = "apiVersion: v1\nkind: ConfigMap\nmetadata:\n  name: " + container.name + "\n  namespace: " + namespace + "\ndata:\n"
  configMapData += "  containerName: " + container.name + "\n"
  configMapData += "  containerImage: " + container.image + "\n"

  println(configMapData)

  def newFile = new File("configmap.yaml")
  newFile.createNewFile() 
  newFile.text = configMapData

  ocCreateConfigMapCmd = "oc apply --overwrite=true -f configmap.yaml"

  println(ocCreateConfigMapCmd)

  def proc = ocCreateConfigMapCmd.execute()
  def b = new StringBuffer()
  proc.consumeProcessErrorStream(b)

  println proc.text
  println b.toString()

  println("----------------------------------------------------------------------------------------------------------")
}

