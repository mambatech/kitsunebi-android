package com.exnor.vray.common

class Constants {
    companion object {
        val PREFERENCE_CONFIG_KEY = "preference_config_key"
        val SUBSCRIBE_CONFIG_URL_KEY = "subscribe_config_url_key"
        val KEY_IP = "IP_CONFIG"
        val KEY_PORT = "port_temp_temp"
        val KEY_UUID = "UUID_CONFIG"


        var BASE_CONFIG = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "${KEY_IP}",
                        ${KEY_PORT},
                        "users": [
                            {
                                "id": "${KEY_UUID}"
                            }
                        ]
                    }
                ]
            },
            "streamSettings": {
                "network": "tcp"
            },
            "tag": "proxy"
        },
        {
            "protocol": "freedom",
            "settings": {
                "domainStrategy": "UseIP"
            },
            "streamSettings": {},
            "tag": "direct"
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "block"
        },
	{
	    "protocol": "dns",
	    "tag": "dns-out"
	}
    ],
    "dns": {
        "clientIp": "115.239.211.92",
        "hosts": {
            "localhost": "127.0.0.1"
        },
        "servers": [
            "114.114.114.114",
            {
                "address": "8.8.8.8",
                "port": 53
            }
        ]
    },
    "log": {
        "loglevel": "warning"
    },
    "policy": {
        "levels": {
            "0": {
                "bufferSize": 409600,
                "connIdle": 30,
                "downlinkOnly": 0,
                "handshake": 4,
                "uplinkOnly": 0
            }
        }
    }
}
        """.trimIndent()

        val SINGAPORE_CONFIG_1 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "139.180.157.99",
                        "port": 12386,
                        "users": [
                            {
                                "id": "ffeb0722-e24e-440f-83a2-ba7d01aa2d81"
                            }
                        ]
                    }
                ]
            },
            "streamSettings": {
                "network": "tcp"
            },
            "tag": "proxy"
        },
        {
            "protocol": "freedom",
            "settings": {
                "domainStrategy": "UseIP"
            },
            "streamSettings": {},
            "tag": "direct"
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "block"
        },
	{
	    "protocol": "dns",
	    "tag": "dns-out"
	}
    ],
    "dns": {
        "clientIp": "115.239.211.92",
        "hosts": {
            "localhost": "127.0.0.1"
        },
        "servers": [
            "114.114.114.114",
            {
                "address": "8.8.8.8",
                "port": 53
            }
        ]
    },
    "log": {
        "loglevel": "warning"
    },
    "policy": {
        "levels": {
            "0": {
                "bufferSize": 409600,
                "connIdle": 30,
                "downlinkOnly": 0,
                "handshake": 4,
                "uplinkOnly": 0
            }
        }
    }
}
        """.trimIndent()

        val SINGAPORE_CONFIG_2 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "139.180.157.99",
                        "port": 12385,
                        "users": [
                            {
                                "id": "b833b684-155b-4af2-b7e5-aea622cb6064"
                            }
                        ]
                    }
                ]
            },
            "streamSettings": {
                "network": "tcp"
            },
            "tag": "proxy"
        },
        {
            "protocol": "freedom",
            "settings": {
                "domainStrategy": "UseIP"
            },
            "streamSettings": {},
            "tag": "direct"
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "block"
        },
	{
	    "protocol": "dns",
	    "tag": "dns-out"
	}
    ],
    "dns": {
        "clientIp": "115.239.211.92",
        "hosts": {
            "localhost": "127.0.0.1"
        },
        "servers": [
            "114.114.114.114",
            {
                "address": "8.8.8.8",
                "port": 53
            }
        ]
    },
    "log": {
        "loglevel": "warning"
    },
    "policy": {
        "levels": {
            "0": {
                "bufferSize": 409600,
                "connIdle": 30,
                "downlinkOnly": 0,
                "handshake": 4,
                "uplinkOnly": 0
            }
        }
    }
}
        """.trimIndent()

        val JAPAN_CONFIG_1 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "45.76.199.109",
                        "port": 27490,
                        "users": [
                            {
                                "id": "382a79db-ac23-4fb8-b4a2-3f18584c82e9"
                            }
                        ]
                    }
                ]
            },
            "streamSettings": {
                "network": "tcp"
            },
            "tag": "proxy"
        },
        {
            "protocol": "freedom",
            "settings": {
                "domainStrategy": "UseIP"
            },
            "streamSettings": {},
            "tag": "direct"
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "block"
        },
	{
	    "protocol": "dns",
	    "tag": "dns-out"
	}
    ],
    "dns": {
        "clientIp": "115.239.211.92",
        "hosts": {
            "localhost": "127.0.0.1"
        },
        "servers": [
            "114.114.114.114",
            {
                "address": "8.8.8.8",
                "port": 53
            }
        ]
    },
    "log": {
        "loglevel": "warning"
    },
    "policy": {
        "levels": {
            "0": {
                "bufferSize": 409600,
                "connIdle": 30,
                "downlinkOnly": 0,
                "handshake": 4,
                "uplinkOnly": 0
            }
        }
    }
}
        """.trimIndent()

        val JAPAN_CONFIG_2 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "45.76.199.109",
                        "port": 27491,
                        "users": [
                            {
                                "id": "96173116-3cb4-48c0-9b2e-14e83210386c"
                            }
                        ]
                    }
                ]
            },
            "streamSettings": {
                "network": "tcp"
            },
            "tag": "proxy"
        },
        {
            "protocol": "freedom",
            "settings": {
                "domainStrategy": "UseIP"
            },
            "streamSettings": {},
            "tag": "direct"
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "block"
        },
	{
	    "protocol": "dns",
	    "tag": "dns-out"
	}
    ],
    "dns": {
        "clientIp": "115.239.211.92",
        "hosts": {
            "localhost": "127.0.0.1"
        },
        "servers": [
            "114.114.114.114",
            {
                "address": "8.8.8.8",
                "port": 53
            }
        ]
    },
    "log": {
        "loglevel": "warning"
    },
    "policy": {
        "levels": {
            "0": {
                "bufferSize": 409600,
                "connIdle": 30,
                "downlinkOnly": 0,
                "handshake": 4,
                "uplinkOnly": 0
            }
        }
    }
}
        """.trimIndent()

    }
}