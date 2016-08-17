# Implemented
1. Implementation for string fields (in and outside of paragraph system)
2. Simple example project with usage
3. Implementation for non string fields (in and outside of paragraph system)
4. Implementation for image fields (in and outside of paragraph system)
5. Default value for @Diff (when old value is null)
6. add @OldVersion that will inject versioned version of property
7. property ./field/propertyName
8. Implementation allows to add own handlers and auto discovery
9. Handler name in diff - example in examples
10. Implementation allows to add handler picking strategy and auto discovery
11. Implementation for string/non string arrays
12. Conditions in @Diff and @OldVersion
13. Default value for @OldValue (when old value is null)
14. Annotation Rank Strategy
15. Sling Models
16. Implementation for string/non string collections slice/sling-models
17. @Children (Both slice and sling models)
18. Annotation pick handler
19. Discovery and implementation for components that do not have fields - filter (adding css class??) (diff from component filter)

# Tests/Implementation
3. install extensions assembly (slung-sling-models, slung-slice, slung-filter - check if it works) + ${project.description} in properties.xml - add description to pom.xml?
3. TextCssClassConfiguration, ImageCssClassConfiguration, ComponentCssClassConfiguration their @Reference should be changed to CssClassConfiguration
4. slice add by reflection
1. Styles for example page + better examples (with meaning) - Material Design (Google)
  - extended list (with customized multifield (list of models))
  - table with numbers and booleans
  - custom conditions (red)
  - custom handlers (number)
  - slice + sightly components
  - sling + jsp components
  - sling + sightly components
  - configuration page (with header) to show same request path condition (check resource.path and request.requestPathInfo.resourcePath - maybe uri is better to compare?)
2. checker framework (jsr 305)
3. upgrade code to java 7
3. ../parent/field/propertyName <- '..' property name handling
4. Manual/Fluent API
5. Implementation that allows html in output (sightly)
6. extension - custom paragraphs - add elements that did not exist previously, sort them | slung-extensions/slung-paragraph-system - custom paragraph system implementation
7. @Diff inject model with versioned everything - @Diff @Inject private SliceModel model - injectionlistener/ sling-models?
9. - slung-slice-configuration (add configuration for slice for rich text and unescaped for @Unescaped annotation)
10. List sort order make as a service - configurable
11. annotation post processor (lombok like) for @Diff int i;

# IMPROVEMENTS
- active - replace with service.enabled?
- pit tests + property tests
- interfaces description
- lazy condition evaluation in condition analyzer
- add install for mvn project
- default values for collections (in Children, in List etc...)
- @Follow from slice/ @Via from sling?
- minimum requirements for sling models (api min 1.0.2, impl 1.0.6) and slice (4.2.1) ?!?!
- upgrade to Slice 4.3.1? Use reflection to be compatible with pre 4.3 post processors registering?
- valuemapvalue and list? what it returns? in condition analyzer context.
- support for sling models interfaces (methods)
- maybe introduce parallel ranking computing?
- semantic versioning
- add pmd / sonar / find bugs

# CHECKS
- two properties with the same name in dialog (is it properly resolved) - not parent/propertyName, parent/propertyName, but parent/x/propertyName, parent/y/propertyName
- ./property/property in lru cache/finding this property name in crx
- all types boolean/Boolean, int/Integer... boolean[]/Boolean[], int[], Integer[]...
- all types Collection<Boolean>, List<Boolean>, Set<Boolean>, SortedSet<Boolean>
- remove warnings (yellow) from project compilation

# BUGS
- why Sightly does not work?
- check why tests fail on windows utf-8 (table ranking)?
- why dialog with two images does not work?

# OTHER
- touch UI suport - no diff button
- slung-plugins - Discovery/Debug in osgi console - Osgi plugin
- publish - leave it as it is or disable DiffHandlerPicker/ or do not install slung-slice (install them through reflection API)/slung-sling-models and other bundles?
- neba support?
- performance - jmh test with automatically generated 500/5000 components on page?

- later - coveralls + travis