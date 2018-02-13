// @cliDescription Creates a component.
// @cliHidden true
const makeRegistry = require('./make-registry')

module.exports = async function (context) {
  const { print, parameters, strings, template, prompt } = context
  const { isBlank, pascalCase, kebabCase } = strings
  const { options } = parameters
  const { colors } = print

  // discover the type of component (component | pure | function)
  let componentType = options.type
  if (!options.type) {
    print.info(colors.muted('--- Type ---'))
    print.info('')
    print.table([
      [colors.yellow('function'), 'Stateless Function Component', colors.muted('great for render-only views')],
      [colors.yellow('pure'), 'React.PureComponent', colors.muted('lightweight views with react lifecycle events')],
      [colors.yellow('component'), 'React.Component', colors.muted('full featured views with state')]
    ])
    print.info('')
    const askType = {
      type: 'list',
      name: 'type',
      message: 'What type of component would you like?',
      choices: ['function', 'pure', 'component']
    }
    const answer = await prompt.ask(askType)
    componentType = answer.type
  }

  // where to save the files
  let location = options.location
  if (!options.location) {
    print.info('')
    print.info(colors.muted('--- Location ---'))
    print.info('')
    print.table([
      [colors.yellow('basics'), colors.muted('simple views with no dependencies (e.g. button, text, label)')],
      [colors.yellow('components'), colors.muted('reusable & more complex views assembled from other parts')],
      [colors.yellow('layouts'), colors.muted('structural templates which hold other components for layout')]
    ])
    print.info('')
    const question = {
      type: 'list',
      name: 'location',
      message: 'What type of component would you like?',
      choices: ['basics', 'components', 'layouts']
    }
    const answer = await prompt.ask(question)
    location = answer.location
    print.info('')
  }

  let name = parameters.first
  if (isBlank(parameters.first)) {
    print.info('')
    print.info(colors.muted('--- Name ---'))
    print.info('')
    print.info(`  Please enter a name for the component.  Please note:`)
    print.info('')
    print.info(`    * kebab-case`)
    print.info(`    * unique name across the entire project`)
    print.info(`    * existing component will be overwritten`)
    print.info('')
    const question = {
      type: 'input',
      name: 'name',
      message: 'Name of component:'
    }
    const answer = await prompt.ask(question)
    if (isBlank(answer.name)) {
      print.error('🚨  ERROR - A name is required.')
      return
    }
    print.info('')
    name = pascalCase(answer.name)
  } else {
    name = pascalCase(parameters.first)
  }

  const kebabName = kebabCase(name)

  const componentTarget = `src/ui/${location}/${kebabName}/${kebabName}.js`
  const testTarget = `src/ui/${location}/${kebabName}/${kebabName}.test.js`

  // create the component
  await template.generate({
    target: componentTarget,
    template: 'component.js.ejs',
    props: { name, kebabName, target: componentTarget, componentType, location }
  })

  // create the test
  await template.generate({
    target: testTarget,
    template: 'component.test.js.ejs',
    props: { name, kebabName, target: testTarget }
  })

  // print what we did
  print.info(
    `  ${print.colors.success(print.checkmark)} ${print.colors.bold(componentTarget)}`
  )
  print.info(
    `  ${print.colors.success(print.checkmark)} ${print.colors.bold(testTarget)}`
  )

  // rebuild the registry
  await makeRegistry(context)

  print.info('')
}
