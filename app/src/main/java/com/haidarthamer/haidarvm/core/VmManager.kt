package com.haidarthamer.haidarvm.core

class VmManager {
    private val vms = linkedMapOf<String, VmConfig>()

    fun add(config: VmConfig) {
        require(config.name.isNotBlank())
        vms[config.name] = config
    }

    fun remove(name: String) {
        vms.remove(name)
    }

    fun list(): List<VmConfig> = vms.values.toList()

    fun get(name: String): VmConfig? = vms[name]
}
