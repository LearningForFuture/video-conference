module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    // add more generic rulesets here, such as:
    'eslint:recommended',
    'plugin:vue/recommended',
    // '@vue/standard',
    // '@vue/prettier',
    "plugin:vue/strongly-recommended",
    "plugin:vue/essential",
  ],
  rules: {
    // override/add rules settings here,
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    // 'vue/no-unused-vars': 'error'
    "vue/script-indent": ["error", 2],
    indent: ["error", 2],
  }
}