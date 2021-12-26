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
    proxy: process.env.VUE_APP_SERVICE_ENDPOINT || 'https://192.168.1.3:3000',
    https: true,
  }
  // disableHostCheck: true,
};