FROM default-route-openshift-image-registry.apps.cluster-2q9gc.2q9gc.sandbox2848.opentlc.com/pacman-ci/rhel9-nodejs-16:latest
USER 0
RUN chmod a+w /var/log
RUN dnf install -y java
COPY groovy/groovy /opt
COPY groovy/startGroovy /opt
#COPY groovy-4.0.26.jar /opt
RUN mkdir /opt/temp
COPY temp/pod-data.json /opt/temp
COPY groovy/groovy-json-4.0.26.jar /opt
copy config-map-generator.groovy /opt
RUN ls -al /opt
USER 1001
RUN export GROOVY_HOME=/opt
CMD ["npm", "start"]
