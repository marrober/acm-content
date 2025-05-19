import groovy.json.JsonSlurper
import java.io.File

println "Object to query : "  + args[0]
println "Namespace : " + args[1]

def namespace = args[1]

def ocGetPodCommand = "oc get " + args[0] + " -n " + args[1] + " -o json"

println(ocGetPodCommand)

def proc = ocGetPodCommand.execute()
def commandResult = new StringBuffer()
proc.consumeProcessErrorStream(commandResult)

jsonText = proc.text
println jsonText

/* def namespace = "cm-1"
String fileContents = new File("./pod-data.json").text

println(fileContents) */

def jsonSlurperComponentTemplates = new JsonSlurper()
def slurpedData=jsonSlurperComponentTemplates.parseText(jsonText)

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

  def process_2 = ocCreateConfigMapCmd.execute()
  def b = new StringBuffer()
  process_2.consumeProcessErrorStream(b)

  println process_2.text
  println b.toString()

  println("----------------------------------------------------------------------------------------------------------")
}

