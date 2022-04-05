#!/usr/bin/env bash

JDK_LINK=https://download.oracle.com/java/17/archive/jdk-17.0.1_linux-x64_bin.deb
JDK_TMP_DIR=/tmp/jdk17.deb
SHARED_DIR=/vagrant/

apt-get update

if ! [ -f $JDK_TMP_DIR ]
    then
    wget -O $JDK_TMP_DIR $JDK_LINK
fi
apt-get install -y $JDK_TMP_DIR
rm -f $JDK_TMP_DIR

if ! grep -qF 'export JAVA_HOME=/usr/lib/jvm/jdk-17/' /etc/profile
    then
    echo 'export JAVA_HOME=/usr/lib/jvm/jdk-17/' | tee -a /etc/profile
fi

if ! grep -qF 'export PATH=$PATH:$JAVA_HOME/bin' /etc/profile
    then
    echo 'export PATH=$PATH:$JAVA_HOME/bin' | tee -a /etc/profile
fi

source /etc/profile


git clone https://github.com/SebMoreno/praxis-gildedrose.git
cd praxis-gildedrose

sh mvnw clean install
cp $SHARED_DIR/gildedrose.service /etc/systemd/system/gildedrose.service
cp target/gildedrose-0.0.1-SNAPSHOT.jar /home/vagrant
systemctl daemon-reload
systemctl enable gildedrose
systemctl start gildedrose