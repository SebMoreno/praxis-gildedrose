# Vagrant Infrastructure Specification


This project has configured two internal infrastructures of Virtual Machines (VMs) managed with Vagrant.

Both environments are meant to be functional after the original build (first ```vagrant up``` call), any restart of the machines (```vagrant reload```), and re-provisioning of the VMs already created without destroying them (```vagrant reload --provision```).

If any problem appears at the moment of using the API, especially if you were doing reboots of individual VMs, restart the api VM is normally enought to fix the issues (```vagrant reload api```).

## DEVELOPMENT ENVIRONMENT

To run the development environment, simply position yourself in the root folder of the repository using your preferred terminal and execute ```vagrant up```

With this all the resources needed to run the application will be configured in two VMs by vagrant. One named ```api``` where all the code in your root folder will be deployed, and another named ```db``` with the postgres database running and listening at the ip configured in the Vagrantfile (```192.168.50.10```).

If the database connection goes wrong then be sure that the application its pointing to the right ip and the port 5433.

## PRODUCTION ENVIRONMENT

This environment is intended to deploy the main branch of the github repository with the same architecture as the development environment. Thus the scripts in the production environment are quite the same as the development environment but with subtle differences, like cloning the repository and not using anything from the shared folder in the VMs except for the scripts and services.

To run this environment just copy the ```prod``` directory into the desired place, then open a terminal on the directory and run ```vagrant up```

## SHARING TO THE INTERNET

The above infrastructures just deploys the application for local network only, if you want to share it to the internet you just need to install the ngrok application on the host machine and run the following comands.

First install the vagrant plugin for sharing

	```vagrant plugin install vagrant-share```

And from now on, when you want to start sharing the running application just run ```vagrant share```, and a link will appear in the terminal, which will be the base url of the API.