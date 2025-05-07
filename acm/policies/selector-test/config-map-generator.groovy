import groovy.json.JsonSlurper

/*def ocGetPodCommand = "oc get pod/my-quarkus-app-run-build-xb6q4-build-sign-image-pod -n  my-quarkus-app-dev -o json"

def proc = ocGetPodCommand.execute()
def b = new StringBuffer()
proc.consumeProcessErrorStream(b)

println proc.text
println b.toString()
*/



String fileContents = new File("/home/marrober/temp/pod-data.json").text

println(fileContents)

def jsonSlurperComponentTemplates = new JsonSlurper()
def slurpedData=jsonSlurperComponentTemplates.parseText(fileContents)

slurpedData.spec.containers.each {  container ->

  //println(container.name)
  //println(container.image)
  configMapData = "apiVersion: v1\nkind: ConfigMap\nmetadata:\n  name: " + container.name + "\n  namespace: default\ndata:\n"
  configMapData += "  containerName: " + container.name + "\n"
  configMapData += "  containerImage: " + container.image + "\n"

  println(configMapData)
  println("----------------------------------------------------------------------------------------------------------")
}

