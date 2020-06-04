package com.exnor.vray.common

class Constants {
    companion object {
        val PREFERENCE_CONFIG_KEY = "preference_config_key"
        val SUBSCRIBE_CONFIG_URL_KEY = "subscribe_config_url_key"
        val JAPAN_CONFIG_1 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "198.13.40.211",
                        "port": 19846,
                        "users": [
                            {
                                "id": "669e8e59-5bd3-4dc9-8d99-654ed6e5645b"
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
                        "address": "198.13.40.211",
                        "port": 19845,
                        "users": [
                            {
                                "id": "950c08cd-b27f-4a58-b80e-66b02fda08da"
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

    }
}