# JsonXml
query JSON by JSONPath or XPath; query XML by XPath

```
usage: JsonXml [options] <input> <query>
input        JSON string or XML string or filename (with option -f)
query        JSONPath string or XPath string (with option -p)
where possible options include:
 -f          load input from file, default regard as string
 -h,--help   print help information
 -i,--info   print debug information
 -p          query is XPath, default is JSONPath
 -x          input is XML format, default is JSON

```
