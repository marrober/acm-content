FROM fedora
USER 0
RUN curl -sLk https://github.com/yudai/gotty/releases/download/v1.0.1/gotty_linux_amd64.tar.gz | tar xzC /usr/local/bin
RUN mkdir /workspace && chmod 777 workspace
RUN chmod a+w /var/log
RUN dnf install -y java
RUN dnf install -y awk
COPY groovy/groovy-4.0.12 /workspace
COPY groovy/groovy-json-4.0.26.jar /workspace
copy generator/config-map-generator.groovy /workspace
RUN ls -al /workspace
RUN curl -sL -o /tmp/oc.tar.gz https://mirror.openshift.com/pub/openshift-v4/clients/ocp/latest/openshift-client-linux.tar.gz && \
tar -C /tmp -xf /tmp/oc.tar.gz --no-same-owner && \
mv /tmp/oc /usr/local/bin && \
chmod +x /usr/local/bin/oc && \
rm /tmp/*
WORKDIR /workspace
EXPOSE 8080
USER 1001
ENV HOME=/workspace
ENV GROOVY_HOME=/workspace
ENV JAVA_HOME=/usr

ENTRYPOINT ["/usr/local/bin/gotty"]
CMD ["-w","bash"]

