module.exports = {
  'env': {
    'browser': true,
    'es2021': true,
  },
  'extends': [
    'google',
  ],
  'parser': '@typescript-eslint/parser',
  'parserOptions': {
    'ecmaVersion': 12,
  },
  'plugins': [
    '@typescript-eslint',
  ],
  'rules': {
    'linebreak-style': ['error', 'unix'],
    'require-jsdoc': ['off'],
    'new-cap': ['off'],
    'max-len': ['error', {'code': 120, 'tabWidth': 2}],
  },
};
