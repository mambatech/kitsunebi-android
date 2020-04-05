package com.mamba.vpn.free.unlimited.hivpn.common

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
                        "port": 19840,
                        "users": [
                            {
                                "id": "6728830e-4f7c-436d-96e1-a70cc90c68e9"
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
                        "port": 19841,
                        "users": [
                            {
                                "id": "3731f5ee-0e87-4af4-8a6a-13b53f5eb363"
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

        val JAPAN_CONFIG_3 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "198.13.40.211",
                        "port": 19842,
                        "users": [
                            {
                                "id": "14b69764-06f1-4a32-9c49-fb27b64f2cc4"
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

        val JAPAN_CONFIG_4 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "198.13.40.211",
                        "port": 19840,
                        "users": [
                            {
                                "id": "9ce827d9-535c-47b0-919c-8eb4297c2680"
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
                        "port": 12281,
                        "users": [
                            {
                                "id": "d5abb8cd-d8ca-4dc5-915e-22585d02c632"
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
                        "port": 12282,
                        "users": [
                            {
                                "id": "25def6fa-a324-4bc4-ba74-e8f8181cee4e"
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

        val SINGAPORE_CONFIG_3 = """
            {
    "outbounds": [
        {
            "protocol": "vmess",
            "settings": {
                "vnext": [
                    {
                        "address": "139.180.157.99",
                        "port": 12283,
                        "users": [
                            {
                                "id": "8be2e8e0-d60e-418f-bd5c-0529b7ceecbe"
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