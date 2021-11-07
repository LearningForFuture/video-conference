module.exports = {
    lintOnSave: true,
    outputDir: 'dist',
    assetsDir: 'assets',
    productionSourceMap: false,
    configureWebpack: {
        performance: {
            hints: false,
        },
        optimization: {
            splitChunks: {
                minSize: 10000,
                maxSize: 250000,
            },
        },
    },
    devServer: {
        proxy: process.env.VUE_APP_SERVICE_ENDPOINT || 'http://localhost:3000',
        headers: {
            "Access-Control-Allow-Origin": "http://localhost:3000",
            "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
        },
    }
    // disableHostCheck: true,
};