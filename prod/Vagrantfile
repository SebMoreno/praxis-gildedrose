Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.define "db" do |db|
    db.vm.network "private_network", ip: "192.168.50.10"
    db.vm.provision "shell", path: "db.bootstrap.sh"
  end

  config.vm.define "api" do |api|
    api.vm.network "private_network", ip: "192.168.50.20"
    api.vm.network :forwarded_port, guest: 8080, host: 80, host_ip: "localhost"
    api.vm.provision "shell", path: "api.bootstrap.sh"
  end
end