// @cliDescription Creates a screen.
// @cliHidden true

const { endsWith } = require('ramdasauce')
const makeRegistry = require('./make-registry')
const makeScreenMap = require('./make-screen-map')

module.exports = async function (context) {
  const { print, parameters, strings, template } = context

  // validate the name
  if (strings.isBlank(parameters.first)) {
    print.error('🚨  ERROR - A name is required.')
    print.info(
      `\nUSAGE\n  $ ignite g screen <Something>${print.colors.muted('Screen')}\n`
    )
    return
  }

  // discover the name
  let name = strings.pascalCase(parameters.first)
  if (!endsWith('Screen', name)) {
    name = name + 'Screen'
  }
  const kebabName = strings.kebabCase(name)

  // where to save the files
  const componentTarget = `src/ui/screens/${kebabName}/${kebabName}.js`
  const testTarget = `src/ui/screens/${kebabName}/${kebabName}.test.js`

  // layout
  const layoutComponent = 'StaticLayout'

  // create the screen
  await template.generate({
    target: componentTarget,
    template: 'screen.js.ejs',
    props: { name, kebabName, target: componentTarget, layoutComponent }
  })

  // create the test
  await template.generate({
    target: testTarget,
    template: 'screen.test.js.ejs',
    props: { name, kebabName, target: testTarget }
  })

  // print what we did
  print.info(
    `  ${print.colors.success(print.checkmark)} ${print.colors.bold(componentTarget)}`
  )
  print.info(
    `  ${print.colors.success(print.checkmark)} ${print.colors.bold(testTarget)}`
  )

  // rebuild the screen stuff
  await makeRegistry(context)
  await makeScreenMap(context)

  print.info('')
}
